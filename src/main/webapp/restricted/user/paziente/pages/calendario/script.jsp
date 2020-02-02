<SCRIPT type="text/javascript">
    $(()=> {
        let calendar = new DayPilot.Calendar("calendar");
        calendar.backendUrl = '${cp}/${calendarioURL}';
        calendar.ajaxError = (err)=> document.body.innerHTML=err.responseText;
        calendar.cssOnly = true;
        calendar.locale = "it-it";
        calendar.cssClassPrefix = "calendar";

        if($(window).width() < 992) {  // mobile view
            calendar.viewType = "Day";
            calendar.tapAndHoldTimeout = 200;
            calendar.eventTapAndHoldHandling = 'Disabled'; //tenendo premuto lo schermo touch apro il ContextMenu
            calendar.onEventClick = function(args) {
                let data = args.e.data;
                alert(data["text"]+'\n' + data["start"] + "\nDott./ssa " + data["resource"]);
            };
        }
        else { // desktop view
            calendar.viewType = "Week";
            calendar.bubble = new DayPilot.Bubble({
                onLoad: function(args) {
                    args.async = true;  // notify manually using .loaded()
                    setTimeout(()=> {
                        let data = args.source.data;
                        args.html = "Dott./ssa " + data["resource"];
                        args.loaded();
                    }, 50);
                }
            });
        }
        calendar.crosshairType = "Full";
        calendar.dayBeginsHour = 8;
        calendar.dayEndsHour = 19;
        calendar.businessBeginsHour = 8;
        calendar.businessEndsHour = 19;
        calendar.cellDuration = 30; //minuti
        calendar.columnWidthSpec = "Fixed";
        calendar.columnWidth = 200;
        calendar.timeFormat = "Clock24Hours";
        calendar.heightSpec = "Parent100Pct";
        calendar.height = 200;
        calendar.headerHeight = 40;
        calendar.weekStarts = 0;
        calendar.shadow = 'Fill';
        calendar.timeRangeSelectedHandling = 'Disabled';
        calendar.eventMoveHandling = 'Disabled';
        calendar.eventResizeHandling = 'Disabled';
        calendar.eventDeleteHandling = 'Disabled';
        calendar.eventClickHandling = 'Disabled';
        calendar.eventRightClickHandling = 'Disabled';
        calendar.eventArrangement = "Full";

        calendar.init(); //installo gli attributo dichiarati sopra

        //ciascun pulsante avrà un compito da svolegere lato server tramite gli attributi dayPilot(previous,today,next)
        $("#previous").on('click',(e)=>calendar.commandCallBack('previous',e));
        $("#today").on('click',(e)=>calendar.commandCallBack('today',e));
        $("#next").on('click',(e)=>calendar.commandCallBack('next',e));

        let picker = new DayPilot.DatePicker();
        picker.theme = "datepicker";
        picker.target = "choose";
        picker.locale = "it-it";
        picker.pattern = "dd-MM-yyyy";
        picker.onTimeRangeSelect = (args)=> calendar.commandCallBack('navigate',args);
        picker.init();

        $("#choose").on('click', function() {
            picker.show();
        });

        setTimeout(()=>document.getElementById("today").click(),100);
        //==============================================================================================================
        document.getElementsByClassName("calendar_corner_inner")[0].nextElementSibling.remove(); // rimuovo il logo "demo"
        document.getElementsByClassName("calendar_main")[0].firstElementChild.classList = "table-panel"; // asseggno una classe di table.css per non riscriverla
    });
</SCRIPT>