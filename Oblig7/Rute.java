import java.util.ArrayList;

abstract class Rute {
  protected int y; //rad
  protected int x; //kolonne
  protected Rute nN;  //naboruter, n for nabo og saa North South East West
  protected Rute nS;
  protected Rute nE;
  protected Rute nW;
  protected Labyrint labyrint;
  protected boolean exit = false;
  protected boolean black;
  protected int visitedCounter; //int som holder styr paa om ruten er besoekt
  protected Rute cameFrom; 
  protected String path; //vei saa langt
  protected Lenkeliste<Rute> crumbs; //ny til oblig 7
  

  Rute(int x, int y, Labyrint labyrint){
    this.visitedCounter = 0;
    this.x = x;
    this.y = y;
    this.nN = null;
    this.nS = null;
    this.nE = null;
    this.nW = null;
    this.path = null;
    this.labyrint = labyrint;
    this.cameFrom = null;
    this.crumbs = null; 


  }
  public String hentTegn(){
    if (this.black) {
      return "|||";
    }
    else {
      return " ";
    }
  }

  //metode for aa returnere en rutes tegn. Sjekker om den er sort, hvis ikke .
  public char tilTegn() {
    if (this.black) {
      return '#';
    }
    else {
      return '.';
    }
  }

  private void gaa(Rute start, int visitedCounter, Rute cameFrom, String path) {
    if (start == null) { //utelukker ruter utenfor labyrint
      return;
    }

    if (start.black) { //utelukker start i vegg
      return;
    }
    //vil kun gaa til ubesoekte ruter
    if (start.visitedCounter < visitedCounter) {
      
      start.visitedCounter += 1;
      start.cameFrom = cameFrom;
      start.crumbs = new Lenkeliste<Rute>();
      if (start.cameFrom == null) {
        start.path = (start.coordinate());
        start.crumbs.leggTil(start);
      }
      else {
        start.path = (start.cameFrom.path + "-->" + start.coordinate());
        for (int i = 0; i<start.cameFrom.crumbs.stoerrelse(); i++){
          start.crumbs.leggTil(start.cameFrom.crumbs.hent(i));
        }
        start.crumbs.leggTil(start);
        
      }
      
      start.gaa(start.nN, visitedCounter, start, start.path);
      start.gaa(start.nE, visitedCounter, start, start.path);
      start.gaa(start.nS, visitedCounter, start, start.path);
      start.gaa(start.nW, visitedCounter, start, start.path);
      
      if (start.exit) { //identifiserer utgang og legger til veien dit i exitPath
        if (start.cameFrom == null){
          start.path = start.coordinate();
          start.labyrint.exitPath.leggTil(start.path);
          start.labyrint.exitList.leggTil(start.crumbs);
        }
        else {
          start.path = (start.cameFrom.path + "-->" + start.coordinate());
          start.labyrint.exitPath.leggTil(start.path);
          start.labyrint.exitList.leggTil(start.crumbs);
        }
      }
    }
  }

  public void finnUtvei(Rute rute) {
    this.gaa(rute, 1, null, null);
  }

  //metode for aa representere en rute med koordinat
  public String coordinate() {
    String q = Integer.toString(this.x);
    String w = Integer.toString(this.y);
    return "("+q+","+w+")";
  }
}
