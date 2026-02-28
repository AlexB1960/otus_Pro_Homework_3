package common;

import dto.Category;
import dto.Tag;
import java.util.ArrayList;
import java.util.Collections;

public abstract class AbsMethodsDTO {
  protected ArrayList<String> setPhotoUrl(String... urlArgs) {
    ArrayList<String> photoUrls = new ArrayList<>();
    /*for (String photoUrl : urlArgs) {
      photoUrls.add(photoUrl);
    }*/
    Collections.addAll(photoUrls, urlArgs);
    return photoUrls;
  }

  protected ArrayList<Tag> setTags(String... tagArgs) {
    ArrayList<Tag> tags = new ArrayList<>();
    int i = 0;
    for (String tagName : tagArgs) {
      Tag tag = Tag.builder()
          .id(0L)
          .name(tagName)
          .build();
      tags.add(tag);
      i++;
    }
    return tags;
  }

  protected Category setCategory(String nameCategory) {
    Category category = Category.builder()
        .id(0L)
        .name(nameCategory)
        .build();
    return category;
  }
}
