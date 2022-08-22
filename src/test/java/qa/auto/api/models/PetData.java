package qa.auto.api.models;

import java.util.Collections;
import java.util.List;

public class PetData extends Data {

    private Integer id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;

    private class Category {
        private Integer id;
        private String name;

        private Category(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private class Tag {
        private Integer id;
        private String name;

        private Tag(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public PetData(Integer id, int categoryId, String categoryName, String name, String photoUrls, int tagId, String tagName) {
        this.id = id;
        this.category = new Category(categoryId, categoryName);
        this.name = name;
        this.photoUrls = Collections.singletonList(photoUrls);
        this.tags = Collections.singletonList(new Tag(tagId, tagName));
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagName() {
        return tags.get(0).name;
    }
}
