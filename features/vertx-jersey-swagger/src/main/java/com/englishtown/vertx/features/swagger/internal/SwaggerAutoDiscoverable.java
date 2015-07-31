package com.englishtown.vertx.features.swagger.internal;

import com.englishtown.vertx.features.swagger.SwaggerFeature;
import com.englishtown.vertx.features.swagger.SwaggerFeatureConfig;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.ws.rs.core.FeatureContext;

/**
 * Swagger auto discoverable feature
 */
@Priority(AutoDiscoverable.DEFAULT_PRIORITY)
public class SwaggerAutoDiscoverable implements AutoDiscoverable {

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(FeatureContext context) {

        if (!context.getConfiguration().isRegistered(SwaggerFeature.class)) {
            context.register(SwaggerFeature.class);
            context.register(new Binder());
        }

    }

    private static class Binder extends AbstractBinder {

        /**
         * Implement to provide binding definitions using the exposed binding
         * methods.
         */
        @Override
        protected void configure() {
            // Use a static instance since ApiListingResource has a static initialized flag (sigh)
            bind(SwaggerServletContext.INSTANCE).to(ServletContext.class);
            bind(JsonSwaggerFeatureConfig.class).to(SwaggerFeatureConfig.class).in(Singleton.class);
        }

    }
}
