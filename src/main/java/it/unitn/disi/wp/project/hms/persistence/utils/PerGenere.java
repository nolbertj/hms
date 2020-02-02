/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.utils;

/**
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 */
public class PerGenere {

    public enum PER_GENERE {
        MALE("M"),FEMALE("F"),ALL("A");

        private String character;

        PER_GENERE(String character){
            this.character=character;
        }

        public String getChar(){
            return this.character;
        }

    }

    static public boolean contains(String character){
        if( PER_GENERE.MALE.character.equals(character) ||
            PER_GENERE.FEMALE.character.equals(character) ||
            PER_GENERE.ALL.character.equals(character))
            return true;
        else
            return false;
    }

    static public PER_GENERE getByCharacter(String character){
        if(PER_GENERE.MALE.character.equals(character)) return PER_GENERE.MALE;
        else if(PER_GENERE.FEMALE.character.equals(character)) return PER_GENERE.FEMALE;
        else if(PER_GENERE.ALL.character.equals(character)) return PER_GENERE.ALL;
        else return null;
    }

}
