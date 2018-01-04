var g;

$( document ).ready(function() {
    g = new JSGantt.GanttChart(document.getElementById('GanttChartDIV'), 'day');

    loadGanttLevel(0);

    //g.AddTaskItem(new JSGantt.TaskItem(1, 'Define Chart API','',          '',          'ggroupblack','', 0, 'Brian', 0,  1,0,1,'','','Some Notes text',g));
    //g.AddTaskItem(new JSGantt.TaskItem(11,'Chart Object',    '2016-02-20','2016-02-20','gmilestone', '', 1, 'Shlomy',100,0,1,1,'','','',g));

});

function loadGanttLevel(id) {
    $.ajax( "/gantt/items?id=" + id )
      .done(function(data) {
      for(var i=0; i<data.length; i++) {
      	    var item = data[i];
            g.AddTaskItem(new JSGantt.TaskItem(item.id, item.viewText,'', '', 'ggroupblack', '', 0, 'Brian', 0, 1, item.parentId, 1, '', '', 'Some Notes text', g));
      	}
        g.Draw();
      })
      .fail(function() {
      })
      .always(function() {
      });
}

