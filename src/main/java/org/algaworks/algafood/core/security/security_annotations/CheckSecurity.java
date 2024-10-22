package org.algaworks.algafood.core.security.security_annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Kitchen {

        @PreAuthorize("hasAuthority('EDIT_KITCHEN') and hasAuthority('SCOPE_WRITE')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Edit {
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }
    }

    @interface Restaurant {

        @PreAuthorize("hasAuthority('EDIT_RESTAURANT') and hasAuthority('SCOPE_WRITE')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Edit {
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }

    }


}
