import java.util.Iterator;

class Lenkeliste<T> implements Liste<T> {
     
    protected class Node { //indre klasse
        protected Node neste;
        protected T data;

        protected Node(T x) {
            data = x;
        }
    }

    protected class LenkelisteIterator implements Iterator<T>{ 
        
        int teller = 0;

        @Override
        public boolean hasNext() { //saa lenge telleren er mindre en lengden av listen, er vi enda ikke i slutten, og derfor har vi en "neste"
            if (teller < stoerrelse()){
                return true;
            } else
            return false;
        }

        @Override
        public T next() { //henter ut innholdet til noden på indeksen definert av teller
            T hentes = hent(teller);
            teller++;
            return hentes;
        }
    }

    protected Node inngang;

    @Override
    public Iterator<T> iterator() {
        return new LenkelisteIterator();
    }

    @Override
    public void leggTil(T x) { //metode som legger til i slutten av listen

        Node nyNode = new Node(x);
        
        if(inngang == null) { //hvis tom liste
            inngang = nyNode;
        }

        else {
            Node hjelpePeker = inngang; //om ikke listen er tom
            while(hjelpePeker.neste != null) { //så lenge neste i listen ikke er tom
                hjelpePeker = hjelpePeker.neste; //peker settes til neste
            }

            hjelpePeker.neste = nyNode; //når neste == null, så blir neste satt til nyNode
        }
    }

    @Override
    public void leggTil(int pos, T x) { //metode som legger inn element på en gitt posisjon

        if(pos < 0 || pos > stoerrelse()) { //hvis pos er utenfor indeks, throw exception
            throw new UgyldigListeIndeks(pos);
        }

        Node nyNode = new Node(x);

        if(inngang == null) { //hvis tom liste, sett inngang til nyNode
            inngang = nyNode;     
        }

        else if(pos == 0) { //hvis man skal sette inn først i lista
            nyNode.neste = inngang;
            inngang = nyNode;
        }


        else{ //om det ikke skal settes først i lista
            int teller = 0;
            Node hjelpePeker = inngang;
            while(teller < (pos -1)) { //må ha -1 fordi man skal stoppe rett før der man skal legge inn
                teller++;
                hjelpePeker = hjelpePeker.neste;
            }
            nyNode.neste = hjelpePeker.neste; //den nye noden sin neste settes til å peke på hjelpePeker sin neste
            hjelpePeker.neste = nyNode; //deretter settes hjelpePekeren sin neste til å peke på noden
        }

    }


    @Override
    public T fjern() { //metode som fjerner første element

        if(inngang == null) { //hvis det ikke finnes noe i listen
            throw new UgyldigListeIndeks(0);
        }

        else if(inngang.neste == null) { //hvis det kun finnes ett element i listen
            T fjernetInnhold = inngang.data;
            inngang = null;
            return fjernetInnhold;
        }

        else { //hvis det finnes flere elementer i listen
            Node hjelpePeker = inngang.neste; //hjelpePekeren peker på den neste i listen
            T fjernetInnhold = inngang.data; //peker på dataen til det første elementet i listen
            inngang = hjelpePeker; //inngang settes til hjelpePeker(som peker på elementet på pos[1])
            return fjernetInnhold;
        }
    }

    @Override
    public T fjern(int pos) { //metode som fjerner et element og returnerer dataen på en gitt posisjon i listen

        if(inngang == null || pos < 0 || pos >= stoerrelse()) { //hvis listen er tom, eller utenfor indeks, throw exception
            throw new UgyldigListeIndeks(0);
        }

        if(pos == 0) { //hvis den skal fjerne første elementet
            T fjernetInnhold = inngang.data;
            inngang = inngang.neste;
            return fjernetInnhold;
        }

        else{ //hvis den skal fjerne element på en gitt posisjon i listen, og returnere dataen til det elementet
            int teller = 0;
            Node hjelpePeker = inngang;
            while(teller < (pos -1)) {
                teller++;
                hjelpePeker = hjelpePeker.neste;
            }
            T fjernetInnhold = hjelpePeker.neste.data;
            hjelpePeker.neste = hjelpePeker.neste.neste;
            return fjernetInnhold;
        }
    }

    @Override
    public T hent(int pos) { //metode for å hente data i en gitt posisjon
        if(pos == 0 && inngang == null || pos < 0 || pos >= stoerrelse()) { //sjekker for ugyldige indekser
            throw new UgyldigListeIndeks(pos);
        }
    
        if(inngang != null) { //dersom listen ikke er tom går den igjennom med en while-loop
            Node hjelpePeker = inngang;
            int teller = 0;
            while (teller <= (pos-1)) { //må ha minus 1 på grunn av posisjon versus indeks
                teller++;
                hjelpePeker = hjelpePeker.neste;
            }
            return hjelpePeker.data;
        }
        return null; //litt usikker på hvorfor jeg må ha denne, men får feil om ikke???
    }

    @Override
    public void sett(int pos, T x) { //metoder som overskriver element i en gitt posisjon
        
        if(inngang == null || pos < 0 || pos >= stoerrelse()) { //sjekker for ugyldige indekser
            throw new UgyldigListeIndeks(pos);
        }

        Node nyNode = new Node(x);

        if(pos == 0 && inngang.neste != null) { //dersom man skal sette inn på starten, og listen ikke er tom
            nyNode.neste = inngang.neste;
            inngang = nyNode;
            return;
        }

        else { //dersom man ikke skal sette inn på starten itererer den til riktig posisjon med en while-loop
            int teller = 0;
            Node hjelpePeker = inngang;
            while(teller < (pos -1)) {
                teller++;
                hjelpePeker = hjelpePeker.neste;
            }
            nyNode.neste = hjelpePeker.neste.neste; //nye noden peker på neste sin neste, og så peker neste på ny node
            hjelpePeker.neste = nyNode; //på den måten setter man inn den nye, og mister referanse til den som var der
            return;
        }

    }

    @Override
    public int stoerrelse() { //metode som returnerer størrelse (antall) i listen
        Node startTeller = inngang;
        int antTeller = 0;
        while(startTeller != null) {
            antTeller++;
            startTeller = startTeller.neste;
        }
        return antTeller;
    }
}