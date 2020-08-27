/**
 * $Id$
 */
package com.advancestores.alexa.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@Schema(description = "Catalog summary")
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class CatalogSummary implements Serializable {
    private static final long serialVersionUID = -4561333457309593796L;

    @Schema(description = "Catalog summary")
    @NonNull
    private String summary;
}
