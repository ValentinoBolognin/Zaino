package it.polito.tdp.zaino;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Zaino {

	private int capienza ; // peso max sopportato dallo zaino
	private List<Pezzo> pezzi ; // pezzi da provare ad inserire
	
	/**
	 * Inizializza un nuovo problema dello zaino
	 * @param capienza Massimo peso che lo zaino può sopportare
	 */
	public Zaino(int capienza) {
		this.capienza = capienza;
		this.pezzi = new ArrayList<Pezzo>();
	}
	
	/**
	 * Aggiunge un nuovo pezzo al problema dello zaino da risolvere. Il nuovo
	 * pezzo deve essere diverso da quelli esistenti.
	 * 
	 * @param p il {@link Pezzo} da aggiungere
	 */
	public void add(Pezzo p) {
		if(!pezzi.contains(p))
			pezzi.add(p) ;
		else
			throw new IllegalArgumentException("Pezzo duplicato "+p);
	}
	
	/**
	 * Calcola il costo di una soluzione parziale
	 * @param parziale
	 * @return
	 */
	private int costo(Set<Pezzo> parziale) {
		int r = 0 ;
		for(Pezzo p : parziale) {
			r += p.getCosto() ;
		}
		return r;
	}
	
	private int peso(Set<Pezzo> parziale) {
		int r = 0 ;
		for(Pezzo p : parziale) {
			r += p.getPeso() ;
		}
		return r;
	}
	
	private void scegli(Set<Pezzo> parziale, int livello, Set<Pezzo> best) {
		
		if(costo(parziale) > costo(best)) {
			// WOW!!! trovato una soluzione migliore
			// Devo SALVARLA in 'best' 
			
			best.clear();
			best.addAll(parziale) ;
			
			System.out.println(parziale);
		}
		
		for(Pezzo p : pezzi) {
			if( !parziale.contains(p)) {
				// 'p' non c'è ancora, provo a metterlo
				// se non supera la capacità
				if( peso(parziale) + p.getPeso() <= capienza ) {
					// prova a mettere 'p' nello zaino
					parziale.add(p);
					// e delegare la  ricerca al livello successivo
					scegli(parziale, livello+1, best);
					// poi rimetti le cose a posto(togli 'p')
					parziale.remove(p);
				}
			}
		}
		
	}
	
	public Set<Pezzo> risolvi() {
		
		Set<Pezzo> parziale = new HashSet<Pezzo>();
		Set<Pezzo> best = new HashSet<Pezzo>();
		scegli(parziale, 0, best) ;
		return best ;
	}
	
	public static void main(String[] args) {
		
		Zaino z = new Zaino(15);
		z.add(new Pezzo(12, 4, "Verde"));
		z.add(new Pezzo(2, 2, "Azzurro"));
		z.add(new Pezzo(1, 1, "Salmone"));
		z.add(new Pezzo(4, 10, "Giallo"));
		z.add(new Pezzo(1, 2, "Grigio"));
		
		Set<Pezzo> soluzione = z.risolvi() ;
		
		System.out.println(soluzione) ;
	}

}
