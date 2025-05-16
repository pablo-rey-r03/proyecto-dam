package org.vedruna.model.dto;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

public class DocumentUploadForm {
    @FormParam("file")
    public InputPart file;
}
