package org.commonspirit.mica.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.adobe.cq.wcm.core.components.models.datalayer.builder.DataLayerBuilder;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;
import com.adobe.cq.wcm.core.components.util.ComponentUtils;
import com.day.cq.commons.Externalizer;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {ImageLinkList.class},
        resourceType = {ImageLinkList.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ImageLinkList extends AbstractComponentImpl {
    protected static final String RESOURCE_TYPE = "mica/components/imagelinklist";
    public static final Logger LOGGER = LoggerFactory.getLogger(ImageLinkList.class);

    @ChildResource
    private Resource links;

    @ChildResource
    private Resource socials;

    @ValueMapValue
    private String selectionType;

    public String getSelectionType(){
        return this.selectionType;
    }
    
    public Boolean isEmpty(){
        int size = 0;
        if(StringUtils.equals( selectionType, "links")){
            size = getLinkObjects().size();
        } else if (StringUtils.equals(selectionType, "images")){
            size = getImageObjects().size();
        }
        return size == 0 ? true : false;
    }

    public List<LinkObject> getLinkObjects(){
        List<LinkObject> linkObjects = new ArrayList<>();

        if(links!=null){
            for (Resource link : links.getChildren()) {
                LinkObject linkObject = link.adaptTo(LinkObject.class);
                linkObject.setParentId(this.getId());
                if(linkObject != null){
                    linkObjects.add(linkObject);
                }
            }
        }
        return linkObjects;
    }

    public List<ImageObject> getImageObjects(){
        List<ImageObject> imageObjects = new ArrayList<>();
        if(socials!=null){

            for(Resource image : socials.getChildren()){
                ImageObject imageObject = image.adaptTo(ImageObject.class);
                imageObject.setParentId(this.getId());
                LOGGER.info("Info: ", imageObject.getData().getTitle());
                if(imageObject!=null){
                    imageObjects.add(imageObject);
                }
            }
        }
        return imageObjects;
    }


    //abstract component
    //check data layer for OOTB link component
    //inherit what is OOTB & use in the components
    //2-3 views of what is happening in list ootb component
    //remind sam to go through ootb data layer and how to implement in custom component
    //how is data layer working, where is it working, where & how it will be used by analytics
    @Model(adaptables = Resource.class)
    public static class LinkObject implements ComponentData{
        @Self
        @Optional
        private Resource resource;

        @ValueMapValue
        @Optional
        private String linkPath;

        @ValueMapValue
        @Optional
        private String linkText;

        @SlingObject
        @Optional
        ResourceResolver resolver;
    
        @OSGiService
        @Optional
        Externalizer externalizer;

        public LinkObject(){}

        private String parentId;

        public String getLinkPath(){
            return getExternalizedPath(this.linkPath);
        }

        public String getLinkText(){
            return this.linkText;
        }

        private String getExternalizedPath(String path){
            return externalizer.externalLink(resolver, Externalizer.LOCAL ,path) + ".html";
        }

        public void setParentId(String parentId){
            this.parentId = parentId;
        }

        public String getParentId(){
            return this.parentId;
        }

        public ComponentData getData(){
            String componentInfo = ComponentUtils.generateId(resource.getName(), resource.getPath());
            return DataLayerBuilder.forComponent()
                .withId(() -> this.getParentId()+"-"+componentInfo)
                .withType(this::getType)
                .withTitle(this::getLinkText)
                .withParentId(this::getParentId)
                .withLinkUrl(this::getLinkPath)
                .build();
        }
    }

    @Model(adaptables = Resource.class)
    public static class ImageObject implements ComponentData{

        @Self
        @Optional
        private Resource resource;

        @ValueMapValue
        @Optional
        private String image;

        @ValueMapValue
        @Optional
        private String imagePath;

        @SlingObject
        @Optional
        ResourceResolver resolver;
    
        @OSGiService
        @Optional
        Externalizer externalizer;

        public ImageObject(){}

        private String parentId;

        public String getImage(){
            return this.image;
        }

        public void setParentId(String parentId){
            this.parentId = parentId;
        }

        public String getParentId(){
            return this.parentId;
        }

        public String getImagePath(){
            return getExternalizedPath(this.imagePath);
        }

        private String getExternalizedPath(String path){
            return externalizer.externalLink(resolver, Externalizer.LOCAL ,path) + ".html";
        }

        public ComponentData getData(){
            String componentInfo = ComponentUtils.generateId(resource.getName(), resource.getPath());
            return DataLayerBuilder.forComponent()
                .withId(() -> this.getParentId()+"-"+componentInfo)
                .withType(this::getType)
                .withTitle(this::getImage)
                .withParentId(this::getParentId)
                .withLinkUrl(this::getImagePath)
                .build();
        }
    }
}
