package by.attrade.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Picture {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(length = 255)
    @NotEmpty
    private String path;

    @NotNull
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "product_gallery_id")
    private Product productGallery;

    @ManyToOne
    @JoinColumn(name = "product_history_gallery_id")
    private Product productHistoryGallery;
}
