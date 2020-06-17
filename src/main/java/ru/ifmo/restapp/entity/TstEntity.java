package ru.ifmo.restapp.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

public class TstEntity {
    @EmbeddedId
    private PairKey key;

//    @Id
//    @GeneratedValue
    private Long /*UUID*/ id;

    @NaturalId
    private String name;


    @Embeddable
    public class PairKey implements Serializable {
        private static final long serialVersionUID = 1L;

        private String keyOne;

        private String keyTwo;

        public String getKeyOne() {
            return keyOne;
        }

        public void setKeyOne(String keyOne) {
            this.keyOne = keyOne;
        }

        public String getKeyTwo() {
            return keyTwo;
        }

        public void setKeyTwo(String keyTwo) {
            this.keyTwo = keyTwo;
        }
    }
}
