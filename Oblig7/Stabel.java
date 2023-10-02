class Stabel<T> extends Lenkeliste<T> {

    public void leggPaa(T x) { //metode som legger på på slutten av listen
        leggTil(x);
    }

    public T taAv() { //metode som tar av på slutten av listen
        return fjern(stoerrelse() -1); //stoerrelse() returnerer antall, men vi vil ha indeks, derav -1
    }
}