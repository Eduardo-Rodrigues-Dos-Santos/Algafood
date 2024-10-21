package org.algaworks.algafood.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

    private String cityName;
    private String stateName;
    private String postalCode;
    private String number;
    private String complement;
    private String district;
}
