package vn.edu.hcmute.kiemtragk.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFilter_24162019 extends ConfigurableSiteMeshFilter {
  @Override
  protected void applyCustomConfiguration(SiteMeshFilterBuilder b) {
    // SiteMesh 3.2 defaults to /WEB-INF/decorators/; retain our existing absolute paths.
    b.setDecoratorPrefix("")
        .addDecoratorPath("/*", "/decorators/web.jsp")
        .addDecoratorPath("/admin", "/decorators/admin.jsp")
        .addDecoratorPath("/admin/*", "/decorators/admin.jsp")
        .addExcludedPath("/login")
        .addExcludedPath("/register")
        .addExcludedPath("/verify-otp")
        .addExcludedPath("/assets/*")
        .addExcludedPath("/decorators/*");
  }
}
