package org.algaworks.algafood.core.security.security_annotations;

import org.springframework.security.access.prepost.PostAuthorize;
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
        @interface Manage {
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }
    }

    @interface Restaurant {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_RESTAURANT') or " +
                "@securityUtils.isResponsibleForRestaurant(#restaurantCode))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Manage {
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }

        @PreAuthorize("isAuthenticated() and (hasAuthority('CONSULT_RESTAURANT') " +
                "or @securityUtils.isResponsibleForRestaurant(#restaurantCode))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface StatisticsConsult {
        }
    }

    @interface Order {

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULT_ORDER') " +
                "or @securityUtils.isResponsibleForRestaurant(#restaurantCode))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface ConsultByRestaurant {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULT_ORDER') " +
                "or @securityUtils.isOwnerTheOrder(#clientId))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface ConsultByClient {

        }

        @PostAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULT_ORDER') " +
                "or @securityUtils.isResponsibleForOrder(#orderCode) " +
                "or @securityUtils.isOwnerTheOrder(returnObject.body.client.id))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('MANAGE_ORDER') or " +
                "@securityUtils.isResponsibleForOrder(#orderCode))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Manage {
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_WRITE')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PlaceOrder {
        }
    }

    @interface Payment {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PAYMENT_METHOD')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Manage {
        }

        @PreAuthorize("isAuthenticated and hasAuthority('SCOPE_READ')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }
    }

    @interface City {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CITY')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Manage {
        }

        @PreAuthorize("isAuthenticated and hasAuthority('SCOPE_READ')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }
    }

    @interface State {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_STATE')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Manage {
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }
    }

    @interface User {

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULT_USER') " +
                "or @securityUtils.isAccountOwner(#userId))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Consult {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_USER') " +
                "or @securityUtils.isAccountOwner(#userId))")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Manage {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_USER')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface ManageGroup {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULT_USER')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface ConsultGroup {
        }

        @PreAuthorize("!isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface Create {
        }
    }
}
