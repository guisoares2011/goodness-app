package imabi.no.kyo.com.goodness.objects;

import android.content.ContentValues;

/**
 * Created by gui-wani on 21/11/2015.
 */
public class Achievement  {
    private Long id;
    private String iconName, name, description;
    private boolean unlocked = false, currentAchievement = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String icon_name) {
        this.iconName = icon_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked == 1;
    }

    public boolean isCurrentAchievement() {
        return currentAchievement;
    }

    public void setCurrentAchievement(int currentAchievement) {
        this.currentAchievement = currentAchievement == 1;
    }

    public ContentValues toContentValues(){
        ContentValues values =  new ContentValues();
        values.put("name", this.getName());
        values.put("description", this.getDescription());
        values.put("current_achievement", this.isCurrentAchievement());
        values.put("icon_name", this.getIconName());
        values.put("id", this.getId());
        values.put("unlocked", this.isUnlocked());
        return values;
    }
}
