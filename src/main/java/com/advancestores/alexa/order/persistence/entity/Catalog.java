/**
 * $Id$
 */
package com.advancestores.alexa.order.persistence.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * JPA Entity class that binds to a relational database table containing Product information.
 *
 * @author $Author$ (last modified by)
 * @version $Revision$
 */
@Entity
@Table(name = "AAP_CATALOG")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class Catalog implements Serializable {

    private static final long serialVersionUID = 2763418196497535188L;

    @Id
    @Column(name = "ID")
    @NonNull
    private Long id;

    @Column(name = "DOMAIN")
    @NonNull
    private String domain;

    @Column(name = "SUMMARY")
    @NonNull
    private String summary;
}
