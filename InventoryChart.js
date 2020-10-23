google.charts.load('current', {packages: ['corechart', 'bar']});
// google.charts.setOnLoadCallback(drawStacked);

//Bind click event to make an ajax call to post request of DataVisualization. This will return
// a json object with top 3 review for each city;
$("#btnGetChartData").click(function () {
     $("#btnGetChartData").hide();
    $.ajax({
        url: "InventoryChart",
        type: "POST",
        data: "{}",
        success: function (msg) {
            createDataTable(msg);      
        },
        error: function(xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            console.log("error occurred while making ajax call;")
            alert(err.Message);
        }
    });    
});

//This method will parse json data and build datatable required by google api to plot the bar chart.
function createDataTable(jsonData) {
    var parsedData = $.parseJSON(jsonData);
    var dataArr = [];
    var headerArr = ['Product', 'Quantity'];
    dataArr.push(headerArr);

    parsedData.forEach(element => {
        var propertyvalues = Object.values(element);
        dataArr.push(propertyvalues);
    });
    console.log(dataArr);

    drawChart(dataArr);
}

//Plot the chart using 2d array and product names as subtitles;
function drawChart(data) {
    //Invoke google's built in method to get data table object required by google.
     var chartData = google.visualization.arrayToDataTable(data);
     var options = {
         title:'Products and Units Left',
         titleTextStyle: {
            color: '#000000'
         },
         width:850, 
         height:1100,
         legend: {
             position:'none'
         },
         vAxis: {
             title: "",
             textStyle: {
             fontSize:14
             }
         },
         chartArea: {top:20, bottom:20}
    }

    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
    chart.draw(chartData, options);
}


