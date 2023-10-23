function showinfoperso(str) {
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function() {
    let valeur = this.responseText;

    for(let i=0;i<valeur.length;i++){
      valeur = valeur.replace("[","")
      valeur = valeur.replace("]","")
      valeur = valeur.replace("\"","")
    }
    
   
    let tmp = 0;
    let tab = new Array();
    while(tmp != -1){
      tmp = valeur.indexOf(",",tmp+1)
      if(tmp !=-1){
        tab.push(tmp)
      }
    }
    
    document.getElementById("age").value = valeur.substring(0, tab[0]);
    document.getElementById("poids").value = valeur.substring(tab[0]+1, tab[1]);
    document.getElementById("taille").value = valeur.substring(tab[1]+1, tab[2]);
    

    let sexe = valeur.substring(tab[2]+1, valeur.length);
    
    if(sexe == "homme"){
      document.getElementById("homme").checked = true;
      document.getElementById("femme").checked = false;
    }
    else if(sexe == "femme"){
      document.getElementById("homme").checked = false;
      document.getElementById("femme").checked = true;
    }
    else{
      document.getElementById("homme").checked = false;
      document.getElementById("femme").checked = false;
    }
  }
  xhttp.open("GET", "getInfoPERSO.php?login="+str);
  xhttp.send(); 
} 

function update(str){

  var age = document.getElementById("age").value
  var poids = document.getElementById("poids").value
  var taille = document.getElementById("taille").value
  var sexe = document.querySelector('input[name="sexe"]:checked').value;

  const xhttp = new XMLHttpRequest();
  xhttp.onload = function() {
    let valeur = this.responseText;

      console.log(valeur+" dans le udpdate")
  }
  xhttp.open("GET", "setInfoPERSO.php?login="+str+"&age="+age+"&poids="+poids+"&taille="+taille+"&sexe="+sexe);
  xhttp.send(); 
}