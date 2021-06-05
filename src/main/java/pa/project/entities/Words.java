package pa.project.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "words")
public class Words {
    @Id
    @Column(name = "word_id")
    private int id;

    @Column(name = "word")
    private String word;

    @Column(name = "word_domain")
    private String wordDomain;
}

