/*
 * BlackBox - jQuery Plugin
 * version: 1.2 (29/04/2013)
 * @requires jQuery v1.4 or later
 *
 *
 * Copyright 2013 Vincent Ting
 * Website http://vincenting.com/
 * License : MIT License / GPL License
 *
 */

(function () {

    var root = this,
    //用于处理同时多个弹出存在
        box_id = 0,
        overlay_list = [],
        load_array = {},
        $W = $(window);

    /**
     * @constructor
     */
    var BlackBox = function () {

        this.init.apply(this, arguments);

    };

    BlackBox.fn = BlackBox.prototype;

    /**
     * 初始化，产生实例的配置
     * clickOverlayEffect: 点击覆盖层效果，默认为抖动 'shake'，可选关闭 'close'
     * overlayColor : 覆盖层颜色，默认为黑色 #000
     * overlayOpacity : 覆盖层透明度，默认为 .6
     * allowPromptBlank ：允许prompt时输入内容为空，默认否，即为空时提交会抖动
     * displayClose：在alert,confirm和prompt模式中显示关闭按钮
     * enableKeyPress：使用快捷键确定和取消
     * @param {Object} config
     */
    BlackBox.fn.init = function (config) {

        var default_config = {
            'clickOverlayEffect': 'shake',
            'overlayColor': '#000',
            'overlayOpacity': .6,
            'allowPromptBlank': false,
            'displayClose': true,
            'enableKeyPress': true
        };

        if (config && Object.prototype.toString.call(config) == '[object Object]') {
            var this_config = {};
            jQuery.each(default_config,
                function (item, value) {
                    this_config[item] = config[item] || value;
                });
            this.config = this_config;
        } else {
            this.config = default_config;
        }
        if (this.config.enableKeyPress) _setKeyShort.call(this);
    };

    /**
     * 载入内容
     * @param item 载入内容
     * @param callback 载入完成执行内容
     * @param _delay_appear
     */
    BlackBox.fn.load = function (item, callback, _delay_appear) {
        if (arguments.length === 0) return;
        callback = callback || $.noop;
        if (!_delay_appear) {
            if (Object.prototype.toString.call(load_array[item]) == '[object Array]') {
                load_array[item].push(callback);
                return;
            }
            load_array[item] = [callback];
            for (var key in load_array) {
                if (key !== item) return;
            }
        } else {
            //延时出现时如果标签为空，说明ready在之前已经执行完毕，不出现任何内容，直接_clearOverlay
            if ((function () {
                for (var key in load_array) {
                    if (load_array.hasOwnProperty(key)) return false;
                }
                return true;
            })()) {
                _clearOverlay.call(this);
                return;
            }
        }
        if (arguments.length === 1) {
            Array.prototype.push.call(arguments, callback);
        }
        if (!_setOverlay.call(this, 'load', arguments, _delay_appear) && !_delay_appear) return;
        $("#BlackBox").append('<div id="BlackBoxLoad">载入中</div>');
        var $BoxLoad = $("#BlackBoxLoad").fadeTo(0, 0).fadeTo(400, 1),
            resize = function () {
                $BoxLoad.css({
                    left: ($W.width() - $BoxLoad.width()) / 2,
                    top: ($W.height() - $BoxLoad.height()) / 2
                })
            };
        resize.call(this);
        $W.resize(resize);
    };

    /**
     * 载入完毕并执行相应内容载入完成时的执行函数
     * @param item
     */
    BlackBox.fn.ready = function (item) {
        if (arguments.length === 0) return;
        var func_list = load_array[item];
        if (func_list) {
            for (var i = 0; i < func_list.length; i++) {
                var func = func_list[i];
                func.call(this);
            }
        }
        delete load_array[item];
        for (var key in load_array) {
            if (load_array.hasOwnProperty(key)) return;
        }
        if (overlay_list[0].type !== "load")return;
        var _this = this;
        $("#BlackBoxLoad").fadeTo(400, 0, function () {
            $(this).remove();
            _clearOverlay.call(_this);
        });
    };

    /**
     * 强制停止载入队列
     * @param callback
     */
    BlackBox.fn.loadClear = function (callback) {
        callback = callback || $.noop;
        for (var key in load_array) {
            if (load_array.hasOwnProperty(key)) {
                delete load_array[key];
            }
        }
        $("#BlackBoxLoad").fadeTo(400, 0);
        _clearOverlay.call(this, callback);
    };

    /**
     * Alert效果
     * @param {String} text
     * @param {Function} callback
     * @param options
     * @param _delay_appear
     */
    BlackBox.fn.alert = function (text, callback, options, _delay_appear) {
        if (arguments.length === 0) return;
        var args = _getArgs.apply(this, arguments);
        if (!_setOverlay.call(this, 'alert', args, args[3]) && !args[3]) return;
        var $BlackBox = $("#BlackBox");
        $BlackBox.append('<div class = "system Inner" id="alert' + _getNowID.call(this) + '"><p>' + args[0] + '</p></div>');
        _boxWrap.call(this, $("#alert" + _getNowID.call(this)), args[2]);
        _setOverlayAttr.call(this);
        if (this.config.displayClose && args[2].title) {
            _setClose.call(this, args[1]);
        }
        _setButton.call(this, args[1], null, args[2]);
    };

    /**
     * Confirm 效果
     * @param text
     * @param callback 确认输入true取消输入false
     * @param options
     * @param _delay_appear
     */
    BlackBox.fn.confirm = function (text, callback, options, _delay_appear) {
        if (arguments.length === 0) return;
        var args = _getArgs.apply(this, arguments);
        if (!_setOverlay.call(this, 'confirm', args, args[3]) && !args[3]) return;
        var $BlackBox = $("#BlackBox");
        $BlackBox.append('<div class = "system Inner" id="confirm' + _getNowID.call(this) + '"><p>' + args[0] + '</p></div>');
        _boxWrap.call(this, $("#confirm" + _getNowID.call(this)), args[2]);
        _setOverlayAttr.call(this);
        var onSubmit = function () {
                return args[1].call(this, true);
            },
            onCancel = function () {
                return args[1].call(this, false);
            };
        if (this.config.displayClose && args[2].title) {
            _setClose.call(this, onCancel);
        }
        _setButton.call(this, onSubmit, onCancel, args[2]);
    };

    /**
     * Prompt 方法
     * @param text
     * @param callback
     * @param options
     * @param _delay_appear
     */
    BlackBox.fn.prompt = function (text, callback, options, _delay_appear) {
        if (arguments.length === 0) return;
        var args = _getArgs.apply(this, arguments);
        if (!_setOverlay.call(this, 'prompt', args, args[3]) && !args[3]) return;
        var verify = args[2].verify ||
            function () {
                return true;
            };
        var $BlackBox = $("#BlackBox");
        $BlackBox.append('<div class = "system Inner" id="prompt' + _getNowID.call(this) + '"><p>' + args[0] + '</p><input id="boxInput"></div>');
        _boxWrap.call(this, $("#prompt" + _getNowID.call(this)), args[2]);
        _setOverlayAttr.call(this);
        var $thisInput = $("#boxInput").focus(),
            onSubmit = function () {
                return args[1].call(this, $thisInput.val());
            },
            onCancel = function () {
                return args[1].call(this, null);
            },
            _this = this;
        $thisInput.blur(function () {
            $(this).val($.trim($(this).val()));
        });
        if (this.config.displayClose && args[2].title) {
            _setClose.call(this, onCancel);
        }
        args[2].verify = function () {
            if ((_this.config.allowPromptBlank || $thisInput.val()) &&
                verify.call(this, $thisInput.val())) {
                return true;
            }
            $thisInput.addClass("boxError").focus();
            return false;
        };
        _setButton.call(this, onSubmit, onCancel, args[2]);
    };

    /**
     * 自定义弹出内容
     * @param html
     * @param _delay_appear
     * @param callback
     */
    BlackBox.fn.popup = function (html, callback, _delay_appear) {
        if (arguments.length === 0) return;
        callback = callback || $.noop;
        if (arguments.length === 1) {
            Array.prototype.push.call(arguments, callback);
        }
        if (!_setOverlay.call(this, 'popup', arguments, _delay_appear) && !_delay_appear)return;
        var $BlackBox = $("#BlackBox");
        $BlackBox.append('<div class="normal" id="popup' + _getNowID.call(this) + '">' + html + '</div>');
        _boxWrap.call(this, $("#popup" + _getNowID.call(this)), {});
        _setOverlayAttr.call(this);
        callback.call(this, $BlackBox);
    };

    /**
     * 删除boxContent的dom并执行回调函数
     * @param callback
     */
    BlackBox.fn.boxClose = function (callback) {
        var $BlackBoxContent = $("#" + _getNowID.call(this)),
            _this = this;
        if (!$BlackBoxContent[0]) return;
        $BlackBoxContent.fadeOut(400,
            function () {
                $(this).remove();
                _clearOverlay.call(_this, callback);
            });
    };

    /**
     * 抖动盒子
     */
    BlackBox.fn.boxShake = function () {
        var $BlackBoxContent = $("#" + _getNowID.call(this));
        if (!$BlackBoxContent[0]) return;
        $BlackBoxContent.stop(true);
        var box_left = $BlackBoxContent.offset().left;
        for (var i = 1; 4 >= i; i++) {
            $BlackBoxContent.animate({
                    left: box_left - (12 - 3 * i)
                },
                50);
            $BlackBoxContent.animate({
                    left: box_left + 2 * (12 - 3 * i)
                },
                50);
        }
    };

    /**
     * 处理自带功能传入参数返回新的arguments
     * @param text
     * @param callback
     * @param options
     * @param _delay_appear
     * @returns {Arguments}
     * @private
     */
    var _getArgs = function (text, callback, options, _delay_appear) {
        callback = callback || $.noop;
        if (!$.isFunction(callback)) {
            options = callback;
            callback = $.noop;
        }
        options = options || {};
        if (arguments.length === 1) {
            Array.prototype.push.call(arguments, callback, options);
        }
        if (arguments.length === 2) {
            Array.prototype.push.call(arguments, options);
        }
        return arguments;
    };

    /**
     * 对box内容进行包裹并保持居中
     * @param $target
     * @param options
     * @private
     */
    var _boxWrap = function ($target, options) {
        $target.wrap('<div class="BlackBoxContent" draggable="true" id="' + _getNowID.call(this) + '"></div>');
        var $BlackBoxContent = $("#" + _getNowID.call(this)).fadeTo(0, 0).fadeTo(400, 1),
            box_width = $BlackBoxContent.width(),
            box_height = $BlackBoxContent.height(),
            resize = function () {
                $BlackBoxContent.css({
                    left: ($W.width() - box_width) / 2 + 'px',
                    top: ($W.height() - box_height - 80) / 2 + 'px'
                })
            };
        if (options.title) {
            $BlackBoxContent.prepend('<p class="title">' + options.title + '</p>');
            var element = $BlackBoxContent, title = $BlackBoxContent.find('.title'), elementX,
                elementY, pointX, pointY, currLeft , currTop,
                preventDefault = function(e){
                    e.stopPropagation();
                    e.preventDefault();
                },
                mouseDown = function (e) {
                    $(document).bind('mousemove', mouseMove);
                    $(document).bind('mouseup', mouseUp);
                    elementX = currLeft = parseFloat(element.css('left'));
                    elementY = currTop = parseFloat(element.css('top'));
                    pointX = e.pageX;
                    pointY = e.pageY;
                    preventDefault(e);
                }, mouseMove = function (e) {
                    currLeft = elementX + e.pageX - pointX;
                    currTop = elementY + e.pageY - pointY;
                    element.css('left', currLeft);
                    element.css('top', currTop);
                    preventDefault(e);
                }, mouseUp = function (e) {
                    $(document).unbind('mousemove', mouseMove);
                    $(document).unbind('mouseup', mouseUp);
                    preventDefault(e);
                };
            title.bind('mousedown', mouseDown);
        }
        $W.resize(resize);
        resize.call(this);
    };

    /**
     * 设置键盘快捷键
     * @private
     */
    var _setKeyShort = function () {
        var _this = this;
        $(document).bind('keydown.fb',
            function (e) {
                var $BlackBoxContent = $(".BlackBoxContent");
                if (!$BlackBoxContent[0])return;
                if (e.keyCode == 13) {
                    $BlackBoxContent.find(".submit").click();
                } else if (e.keyCode == 27) {
                    var button = $BlackBoxContent.find(".close")[0] || $BlackBoxContent.find(".cancel")[0] || $BlackBoxContent.find(".submit")[0];
                    if (button) {
                        $(button).click();
                    } else {
                        _this.boxClose.call(_this);
                    }
                }
            });
    };

    /**
     * 产生遮罩
     * @returns {boolean}
     * @private
     */
    var _setOverlay = function (type, args, delay_appear) {
        box_id += 1;
        if (!delay_appear) {
            overlay_list.push({
                id: box_id,
                type: type,
                args: args
            });
        }
        if (overlay_list.length !== 1) {
            return false;
        }
        if (!$("#BlackBox")[0]) {
            $("body").append('<div id="BlackBox"><div id="BBOverlay"></div></div>');
        }
        var $BBOverlay = $("#BBOverlay"),
            config = this.config;
        if (!delay_appear) {
            $BBOverlay.css({
                background_color: config.overlayColor
            }).fadeTo(0, 0).fadeTo(400, config.overlayOpacity);
        }
        var resize = function () {
            $BBOverlay.width($W.width() + "px").height($W.height() + "px");
        };
        resize.call(this);
        $W.resize(resize);
        return true;
    };

    /**
     * 删除遮罩，如果强制删除或者只有一个弹出内容时直接删除dom，否则显示下一个操作
     * @private
     * @param force
     * @param callback
     */
    var _clearOverlay = function (callback, force) {
        if (callback) callback.call(this);
        if (force) {
            overlay_list.length = 0;
        } else {
            overlay_list.shift();
        }
        $("#BBOverlay").unbind("click");
        if (overlay_list.length == 0) {
            var $BlackBox = $("#BlackBox");
            if (!$BlackBox[0]) return;
            $BlackBox.fadeOut(400,
                function () {
                    $(this).remove();
                });
        } else {
            //执行队列中下一个内容
            Array.prototype.push.call(overlay_list[0].args, true);
            this[overlay_list[0].type].apply(this, overlay_list[0].args);
        }
    };

    /**
     * 根据config内容设置遮罩点击效果
     * @private
     */
    var _setOverlayAttr = function () {
        var click_effect = this.config.clickOverlayEffect,
            $BlackBoxContent = $("#" + _getNowID.call(this)),
            $BBOverlay = $("#BBOverlay"),
            _this = this;
        if (click_effect === 'close') {
            $BBOverlay.click(function () {
                var button = $BlackBoxContent.find(".close")[0] || $BlackBoxContent.find(".cancel")[0] || $BlackBoxContent.find(".submit")[0];
                if (button) {
                    $(button).click();
                } else {
                    _this.boxClose.call(_this);
                }
            });
            return;
        }
        $BBOverlay.click(function () {
            var $input = $BlackBoxContent.find("input");
            if ($input.length === 1) {
                $input.focus();
            }
            _this.boxShake.call(_this);
        })
    };

    /**
     * 根据传入的参数来添加确定取消按钮
     * @param onSubmit
     * @param onCancel
     * @param options
     * @private
     */
    var _setButton = function (onSubmit, onCancel, options) {
        if (arguments.length === 0) return;
        var $BlackBoxContent = $("#" + _getNowID.call(this)),
            _this = this;
        $BlackBoxContent.find(".Inner").append('<div id="BlackBoxAction"></div>');
        var $BlackBoxAction = $("#BlackBoxAction");
        if (onCancel) {
            $BlackBoxAction.append('<button class="cancel">取消</button>');
            $BlackBoxAction.find(".cancel").click(function () {
                _this.boxClose.call(_this, onCancel);
            })
        }
        if (onSubmit) {
            var button_text = options.value || '确定';
            $BlackBoxAction.append('<button class="submit">' + button_text + '</button>');
            $BlackBoxAction.find(".submit").click(function () {
                if (!options.verify || options.verify.call(_this)) {
                    _this.boxClose.call(_this, onSubmit);
                } else {
                    _this.boxShake.call(_this);
                }
            })
        }
    };

    /**
     * 添加关闭按钮
     * @param onCancel
     * @private
     */
    var _setClose = function (onCancel) {
        var $BlackBoxContent = $("#" + _getNowID.call(this)),
            _this = this;
        $BlackBoxContent.append('<div class="close">&times;</div>');
        $BlackBoxContent.find(".close").click(function () {
            _this.boxClose.call(_this, onCancel);
        })
    };

    /**
     * 返回当前的box的id
     * @returns {string}
     * @private
     */
    var _getNowID = function () {
        var id = overlay_list[0].id;
        return "_box_" + id;
    };

    root.BlackBox = BlackBox;

}).call(window);