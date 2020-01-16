/*-
 * #%L
 * ash :: Bundles :: Add-on :: System Update
 * %%
 * Copyright (C) 2015 - 2020 aleon GmbH
 * %%
 * ***
 * aleon GmbH
 * Sigmannser Weg 2
 * D-88239 Wangen
 * phone: +49 (0)7522 2654-100
 * fax:   +49 (0)7522 2654-149
 * mail:  contact@aleon.eu
 * ***
 * All rights reserved!
 * Proprietary and confidential! Do not redistribute!
 * Unpublished work. All rights reserved under the German copyright laws.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * The software must not be copied, distributed, changed, rewritten and / or
 * re-factored without the express permission of the copyright holder.
 * #L%
 */

package de.maggu2810.playground.multipart.servlet;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

@Component(immediate = true)
public class Service {

    private static final String ALIAS = "/upload";

    @Reference
    protected HttpService httpService;

    @Activate
    protected void activate(final BundleContext bundleContext) throws ServletException, NamespaceException {
        final String tmpdir = System.getProperty("java.io.tmpdir");
        final Path uploadPath = Paths.get(tmpdir, "multipart-test", "upload");
        uploadPath.toFile().mkdirs();
        httpService.registerServlet(ALIAS, new UploadServlet(uploadPath), null, null);
    }

    @Deactivate
    protected void deactivate(final ComponentContext componentContext) {
        httpService.unregister(ALIAS);
    }

}
