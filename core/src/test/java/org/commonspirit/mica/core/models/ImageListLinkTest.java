// package org.commonspirit.mica.core.models;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.lenient;

// import java.util.ArrayList;
// import java.util.List;

// import org.apache.sling.api.resource.Resource;
// import org.commonspirit.mica.core.models.ImageLinkList.LinkObject;
// import org.commonspirit.mica.core.testcontext.AppAemContext;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.adobe.cq.wcm.core.components.util.ComponentUtils;

// import io.wcm.testing.mock.aem.junit5.AemContext;
// import io.wcm.testing.mock.aem.junit5.AemContextExtension;

// @ExtendWith({AemContextExtension.class, MockitoExtension.class})
// public class ImageListLinkTest {
//     private final AemContext ctx = AppAemContext.newAemContext();

//     @Mock
//     Resource resource;

//     @BeforeEach
//     public void setup() throws Exception {
//         ctx.addModelsForClasses(ImageLinkList.class);
//         ctx.load().json("/java/org/commonspirit/mica/core/models/ImageLinkListTest.json", "/content");
//         lenient().when(ComponentUtils.isDataLayerEnabled(resource)).thenReturn(true);
//     }

//     @Test
//     public void imageSelectionTest(){
//         ctx.currentResource("/content/imageList");
//         ImageLinkList imageLinkList = ctx.request().adaptTo(ImageLinkList.class);

//         assertEquals("images", imageLinkList.getSelectionType());
//         assertEquals(2, imageLinkList.getImageObjects().size());
//         assertEquals(0, imageLinkList.getLinkObjects().size());
//         assertEquals("null", imageLinkList.getData());
//     }
    
//     @Test
//     public void linkSelectionTest(){
//         ctx.currentResource("/content/linkList");
//         ImageLinkList imageLinkList = ctx.request().adaptTo(ImageLinkList.class);

//         assertEquals("links", imageLinkList.getSelectionType());
//         assertEquals(2, imageLinkList.getLinkObjects().size());
//         assertEquals("Link 1", imageLinkList.getLinkObjects().get(0).getLinkText());
//         assertEquals("/content/apps/link1.html", imageLinkList.getLinkObjects().get(0).getLinkPath());
//         assertEquals("Link 2", imageLinkList.getLinkObjects().get(1).getLinkText());
//         assertEquals("/content/apps/link2.html", imageLinkList.getLinkObjects().get(1).getLinkPath());

//     }
// }
