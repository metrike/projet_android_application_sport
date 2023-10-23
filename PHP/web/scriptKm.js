function showinfokm(str) {
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function() {
    let valeur = this.responseText;
    graphiqueKm(valeur);
  }
  xhttp.open("GET", "getInfoKM.php?login="+str);
  xhttp.send(); 
}

function graphiqueKm(tableau_km) {
  var taille = -1;
  var dateDebut;
  var dateFin;

  if(tableau_km.length == 0){
    document.getElementById("chartContainer2").innerHTML = "Pas d'activités";
  } else {

    //----------Refaire un tab : 
    let array = tableau_km.replace(/\u0022/g, "").replace(/\u007B/g, "").replace(/\u007D/g, "").split(",");
    let tableau_final = [];
    let dates = [];
    
    array.forEach(element => {
        taille ++;
        let a =  element.split(":");
        dates[taille] = a[0];
        tableau_final[taille] = a[1];
    });

    let data1 = dates[0].split("-");
    dateDebut = new Date(data1[0], data1[1]-1, data1[2]);
    let data2 = dates[taille].split("-");
    dateFin = new Date(data2[0], data2[1]-1, data2[2]);
      
    //---------------------
    var dataKm = [];
  
    for (let i = 0; i <= taille; i++) {
      let data = dates[i].split("-");
      dataKm.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i])});
    }
  
    var stockChart = new CanvasJS.StockChart("chartContainer2",{
      theme: "light2",
      animationEnabled: true,
      title:{
        text:"La distance que vous avez parcouru"
      },
      subtitles: [{
        text: "Depuis votre première activité"
      }],
      charts: [{
        axisY: {
          title: "Distance (en km)"
        },
        toolTip: {
          shared: true
        },
        legend: {
              cursor: "pointer",
              itemclick: function (e) {
                if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible)
                  e.dataSeries.visible = false;
                else
                  e.dataSeries.visible = true;
                e.chart.render();
              }
          },
        data: [{
          showInLegend: true,
          name: "Km",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataKm
        }],
      }],
      rangeSelector: {
        enabled: false
      },
      navigator: {
        data: [{
          dataPoints: dataKm
        }],
        slider: {
          minimum: dateDebut,
          maximum: dateFin
        }
      }
    });
  
    stockChart.render();
  }
}