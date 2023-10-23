function showinfosport(str) {
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function() {
    let valeur = this.responseText;
    graphique(valeur);
  }
  xhttp.open("GET", "getInfoSPORT.php?login="+str);
  xhttp.send(); 
}

function graphique(tableau) {
  var taille = -1;
  var dateDebut;
  var dateFin;

  if(tableau.length == 0){
    document.getElementById("chartContainer1").innerHTML = "";
  } else {

    //----------Refaire un tab : 
    let array = tableau.replace(/\u0022/g, "").replace(/\u005B/g, "").replace(/\u005D/g, "").split("},");
    let tab = [];
    let tableau_final = [];
    let dates = [];
    array.forEach(element => {
        let arra = element.replace(/\u007D/g, "").split(":{");
        tab.push(arra);
    });
    tab.forEach(element => {
      taille++;
      let t = [];
      let activite = element[1].split(",");
      activite.forEach(e => {
        let z =  e.split(":");
        t[z[0]] = z[1];
      });
      dates[taille] = element[0].replace(/\u007B/g, "");
      tableau_final[taille] = t;
      
    });
    let data1 = dates[0].split("-");
    dateDebut = new Date(data1[0], data1[1]-1, data1[2]);
    let data2 = dates[taille].split("-");
    dateFin = new Date(data2[0], data2[1]-1, data2[2]);
  
    //---------------------
    var dataCourse = [];
    var dataVelo = [];
    var dataBasket = [];
    var dataFootball = [];
    var dataNatation = [];
    var dataMusculation = [];
    var dataAviron = [];
    var dataSki = [];
    var dataHandball = [];
    var dataGolf = [];
  
    for (let i = 0; i <= taille; i++) {
      let data = dates[i].split("-");
      dataCourse.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Course)});
      dataVelo.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Velo)});
      dataBasket.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Basket)});
      dataFootball.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Football)});
      dataNatation.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Natation)});
      dataMusculation.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Musculation)});
      dataAviron.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Aviron)});
      dataSki.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Ski)});
      dataHandball.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Handball)});
      dataGolf.push({x: new Date(data[0],data[1]-1,data[2]), y: Number(tableau_final[i].Golf)});
    }
  
    var stockChart = new CanvasJS.StockChart("chartContainer1",{
      theme: "light2",
      animationEnabled: true,
      title:{
        text:"Vos activités"
      },
      subtitles: [{
        text: "Depuis votre première activité"
      }],
      charts: [{
        axisY: {
          title: "Nombre d'activité"
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
          name: "Course",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataCourse
        },{
          showInLegend: true,
          name: "Vélo",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataVelo
        },{
          showInLegend: true,
          name: "Basket",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataBasket
        },{
          showInLegend: true,
          name: "Football",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataFootball
        },{
          showInLegend: true,
          name: "Natation",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataNatation
        },{
          showInLegend: true,
          name: "Musculation",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataMusculation
        },{
          showInLegend: true,
          name: "Aviron",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataAviron
        },{
          showInLegend: true,
          name: "Ski",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataSki
        },{
          showInLegend: true,
          name: "Handball",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataHandball
        },{
          showInLegend: true,
          name: "Golf",
          yValueFormatString: "#,##0",
          xValueType: "dateTime",
          dataPoints : dataGolf
        }]
      }],
      rangeSelector: {
        enabled: false
      },
      navigator: {
        data: [{
          dataPoints: dataCourse
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