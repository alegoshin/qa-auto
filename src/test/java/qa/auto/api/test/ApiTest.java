package qa.auto.api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import qa.auto.api.base.ApiBaseState;
import qa.auto.api.models.PetData;

import java.util.concurrent.ThreadLocalRandom;

public class ApiTest extends ApiBaseState {

    private Integer petId = null;

    @Test()
    public void postPetTest() {

        PetData petData = new PetData(ThreadLocalRandom.current().nextInt(0, 999),
                0, "dog", "pushok",
                "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png",
                0, "pu");

        PetData pet = post("pet", petData, 200).as(PetData.class);

        Assert.assertEquals(
                pet.getId(),
                petData.getId(),
                "Pet with another id was created");
        Assert.assertEquals(
                pet.getName(),
                petData.getName(),
                "Pet with another name was created");

        petId = pet.getId();
    }

    @Test(dependsOnMethods = {"postPetTest"}, priority = 1)
    public void getPetTest() {

        PetData pet = get("pet/" + petId, 200).as(PetData.class);

        Assert.assertEquals(
                pet.getId(),
                petId,
                "Pet with another id was returned");
    }

    @Test(dependsOnMethods = {"postPetTest"}, priority = 1)
    public void putPetTest() {

        PetData petData = new PetData(petId,
                0, "dog", "sharik",
                "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png",
                0, "sha");

        PetData pet = put("pet", petData, 200).as(PetData.class);
        Assert.assertEquals(
                pet.getId(),
                petId,
                "Id was changed");
        Assert.assertEquals(
                pet.getName(),
                petData.getName(),
                "Pet name wasn't correctly changed");
        Assert.assertEquals(
                pet.getTagName(),
                petData.getTagName(),
                "Tag name wasn't correctly changed");
    }

    @Test(dependsOnMethods = {"postPetTest"}, priority = 2)
    public void deletePetTest() {

        delete("pet/" + petId, null, 200);

        String response = get("pet/" + petId, 404).asString();

        Assert.assertEquals(
                response,
                "{\"code\":1,\"type\":\"error\",\"message\":\"Pet not found\"}",
                "Pet wasn't removed");
    }

    @Test(dependsOnMethods = {"postPetTest", "deletePetTest"}, priority = 3)
    public void getDeletedPetTest() {

        String message = get("pet/" + petId, 404).jsonPath().getString("message");

        Assert.assertEquals(
                message,
                "Pet not found",
                "Wrong error message was returned");
    }

}