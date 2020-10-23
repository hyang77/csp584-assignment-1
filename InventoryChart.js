google.charts.load('current', {packages: ['corechart', 'bar']});
// google.charts.setOnLoadCallback(drawStacked);

//Bind click event to make an ajax call to post request of DataVisualization. This will return
// a json object with top 3 review for each city;
$("#btnGetChartData").click(function () {
     $("#btnGetChartData").hide();
    $.ajax({
        url: "Inventory",
        type: "POST",
        data: "{}",
        success: function (msg) {
            console.log(msg);          
        },
        error: function(xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            console.log("error occurred while making ajax call;")
            alert(err.Message);
        }
    });    
});
