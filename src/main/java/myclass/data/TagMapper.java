package myclass.data;

import myclass.model.Tag;
import myclass.model.TagMap;

public interface TagMapper {
	Tag selectTagByName(String name);
	void insertTag(Tag tag);
	void insertTagMap(TagMap tagMap);
	TagMap selectTagMap(TagMap tagMap);
}
