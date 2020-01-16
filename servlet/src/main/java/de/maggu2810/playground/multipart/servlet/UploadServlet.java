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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map.Entry;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet that handles the upload.
 */
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** The size threshold after which the file will be written to disk. */
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024 * 2;

    /** The maximum size allowed for uploaded files (-1L means unlimited). */
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 1024;

    /** The maximum size allowed for "multipart/form-data" requests (-1L means unlimited). */
    private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 1024;

    private final Logger logger = LoggerFactory.getLogger(UploadServlet.class);

    private final File tempDir;

    public UploadServlet(final Path tempDir) {
        this.tempDir = tempDir.toFile();
    }

    @Override
    public void init() throws ServletException {
        /*
         * @MultipartConfig annotation for the class does not seem to work.
         * So let's configure it programmatically.
         */
        final MultipartConfigElement mc = new MultipartConfigElement(tempDir.getAbsolutePath(), MAX_FILE_SIZE,
                MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);

        for (final Entry<String, ? extends ServletRegistration> entry : getServletContext().getServletRegistrations()
                .entrySet()) {
            final ServletRegistration reg = entry.getValue();
            if (reg == null) {
                continue;
            }
            if (reg instanceof ServletRegistration.Dynamic) {
                final ServletRegistration.Dynamic regDyn = (ServletRegistration.Dynamic) reg;
                regDyn.setMultipartConfig(mc);
            }
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Handle POST request");
        final Collection<Part> parts = request.getParts();
        logger.info("Got parts: {}", parts);
    }

}
