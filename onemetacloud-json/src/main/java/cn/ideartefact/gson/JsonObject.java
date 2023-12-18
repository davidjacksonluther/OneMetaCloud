/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ideartefact.gson;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import cn.ideartefact.gson.internal.LinkedTreeMap;

import java.util.*;

/**
 * A class representing an object type in Json. An object consists of name-value pairs where names
 * are strings, and values are any other type of {@link JsonElement}. This allows for a creating a
 * tree of JsonElements. The member elements of this object are maintained in order they were added.
 * This class does not support {@code null} values. If {@code null} is provided as value argument to
 * any of the methods, it is converted to a {@link JsonNull}.
 *
 * <p>{@code JsonObject} does not implement the {@link Map} interface, but a {@code Map} view of it
 * can be obtained with {@link #asMap()}.
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 */
public final class JsonObject extends JsonElement {
  private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap<>(false);

  /** Creates an empty JsonObject. */
  @SuppressWarnings("deprecation") // superclass constructor
  public JsonObject() {}

  /**
   * Creates a deep copy of this element and all its children.
   *
   * @since 2.8.2
   */
  @Override
  public JsonObject deepCopy() {
    JsonObject result = new JsonObject();
    for (Map.Entry<String, JsonElement> entry : members.entrySet()) {
      result.add(entry.getKey(), entry.getValue().deepCopy());
    }
    return result;
  }

  /**
   * Adds a member, which is a name-value pair, to self. The name must be a String, but the value
   * can be an arbitrary {@link JsonElement}, thereby allowing you to build a full tree of
   * JsonElements rooted at this node.
   *
   * @param property name of the member.
   * @param value the member object.
   */
  public void add(String property, JsonElement value) {
    members.put(property, value == null ? JsonNull.INSTANCE : value);
  }

  /**
   * Removes the {@code property} from this object.
   *
   * @param property name of the member that should be removed.
   * @return the {@link JsonElement} object that is being removed, or {@code null} if no member with
   *     this name exists.
   * @since 1.3
   */
  @CanIgnoreReturnValue
  public JsonElement remove(String property) {
    return members.remove(property);
  }

  /**
   * Convenience method to add a string member. The specified value is converted to a {@link
   * JsonPrimitive} of String.
   *
   * @param property name of the member.
   * @param value the string value associated with the member.
   */
  public void addProperty(String property, String value) {
    add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
  }

  /**
   * Convenience method to add a number member. The specified value is converted to a {@link
   * JsonPrimitive} of Number.
   *
   * @param property name of the member.
   * @param value the number value associated with the member.
   */
  public void addProperty(String property, Number value) {
    add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
  }

  /**
   * Convenience method to add a boolean member. The specified value is converted to a {@link
   * JsonPrimitive} of Boolean.
   *
   * @param property name of the member.
   * @param value the boolean value associated with the member.
   */
  public void addProperty(String property, Boolean value) {
    add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
  }

  /**
   * Convenience method to add a char member. The specified value is converted to a {@link
   * JsonPrimitive} of Character.
   *
   * @param property name of the member.
   * @param value the char value associated with the member.
   */
  public void addProperty(String property, Character value) {
    add(property, value == null ? JsonNull.INSTANCE : new JsonPrimitive(value));
  }

  /**
   * Returns a set of members of this object. The set is ordered, and the order is in which the
   * elements were added.
   *
   * @return a set of members of this object.
   */
  public Set<Map.Entry<String, JsonElement>> entrySet() {
    return members.entrySet();
  }

  /**
   * Returns a set of members key values.
   *
   * @return a set of member keys as Strings
   * @since 2.8.1
   */
  public Set<String> keySet() {
    return members.keySet();
  }

  /**
   * Returns the number of key/value pairs in the object.
   *
   * @return the number of key/value pairs in the object.
   * @since 2.7
   */
  public int size() {
    return members.size();
  }

  /**
   * Returns true if the number of key/value pairs in the object is zero.
   *
   * @return true if the number of key/value pairs in the object is zero.
   * @since 2.10.1
   */
  public boolean isEmpty() {
    return members.size() == 0;
  }

  /**
   * Convenience method to check if a member with the specified name is present in this object.
   *
   * @param memberName name of the member that is being checked for presence.
   * @return true if there is a member with the specified name, false otherwise.
   */
  public boolean has(String memberName) {
    return members.containsKey(memberName);
  }

  /**
   * Returns the member with the specified name.
   *
   * @param memberName name of the member that is being requested.
   * @return the member matching the name, or {@code null} if no such member exists.
   */
  public JsonElement get(String memberName) {
    return members.get(memberName);
  }

  /**
   * Convenience method to get the specified member as a {@link JsonPrimitive}.
   *
   * @param memberName name of the member being requested.
   * @return the {@code JsonPrimitive} corresponding to the specified member, or {@code null} if no
   *     member with this name exists.
   * @throws ClassCastException if the member is not of type {@code JsonPrimitive}.
   */
  public JsonPrimitive getAsJsonPrimitive(String memberName) {
    return (JsonPrimitive) members.get(memberName);
  }

  /**
   * Convenience method to get the specified member as a {@link JsonArray}.
   *
   * @param memberName name of the member being requested.
   * @return the {@code JsonArray} corresponding to the specified member, or {@code null} if no
   *     member with this name exists.
   * @throws ClassCastException if the member is not of type {@code JsonArray}.
   */
  public JsonArray getAsJsonArray(String memberName) {
    return (JsonArray) members.get(memberName);
  }

  /**
   * Convenience method to get the specified member as a {@link JsonObject}.
   *
   * @param memberName name of the member being requested.
   * @return the {@code JsonObject} corresponding to the specified member, or {@code null} if no
   *     member with this name exists.
   * @throws ClassCastException if the member is not of type {@code JsonObject}.
   */
  public JsonObject getAsJsonObject(String memberName) {
    return (JsonObject) members.get(memberName);
  }

  /**
   * Returns a mutable {@link Map} view of this {@code JsonObject}. Changes to the {@code Map} are
   * visible in this {@code JsonObject} and the other way around.
   *
   * <p>The {@code Map} does not permit {@code null} keys or values. Unlike {@code JsonObject}'s
   * {@code null} handling, a {@link NullPointerException} is thrown when trying to add {@code
   * null}. Use {@link JsonNull} for JSON null values.
   *
   * @return mutable {@code Map} view
   * @since 2.10
   */
  public Map<String, JsonElement> asMap() {
    // It is safe to expose the underlying map because it disallows null keys and values
    return members;
  }

  /**
   * Returns whether the other object is equal to this. This method only considers the other object
   * to be equal if it is an instance of {@code JsonObject} and has equal members, ignoring order.
   */
  @Override
  public boolean equals(Object o) {
    return (o == this) || (o instanceof JsonObject && ((JsonObject) o).members.equals(members));
  }

  /**
   * Returns the hash code of this object. This method calculates the hash code based on the members
   * of this object, ignoring order.
   */
  @Override
  public int hashCode() {
    return members.hashCode();
  }


  /**
   *Extend JsonObject MemberMethod
   * Author : David.Ding
   * Email: servantdjrk@hotmail.com
   */

  public JsonObject getJsonObject(String key) throws JsonObjectValueTypeError {
    LinkedList<String[]> treeKeyList =  parseKeyTreeList(key);
    JsonObject result =  getJsonObject(treeKeyList);
    return result;

  }

  public JsonArray getJsonArray(String key) throws JsonObjectValueTypeError {
    LinkedList<String[]> treeKeyList =  parseKeyTreeList(key);
    JsonArray result =  getJsonArray(treeKeyList);
    return result;

  }

  public String getString(String key) throws JsonObjectValueTypeError {
    LinkedList<String[]> treeKeyList =  parseKeyTreeList(key);
    String result =  getString(treeKeyList);
    return result;

  }

  private LinkedList<String[]> parseKeyTreeList(String Key) {

    List<String> keyList = Arrays.stream(Key.split("\\.")).toList();
    Iterator iterator = keyList.iterator();
    LinkedList<String[]> KeyTreeList = new LinkedList<>();
    while (iterator.hasNext()) {
      String layerKey = (String) iterator.next();
      if (layerKey.matches("[a-zA-Z0-9_]*\\[[0-9]{1,}\\]")) {
        int leftBracketIndex = layerKey.indexOf('[');
        int rightBracketIndex = layerKey.indexOf(']');
        String JsonObjectKey = layerKey.substring(0, leftBracketIndex);
        int JsonArrayIndex = Integer.parseInt(layerKey.substring(leftBracketIndex + 1, rightBracketIndex ));
        String JsonArrayIndexStr = String.valueOf(JsonArrayIndex);
        System.out.println(JsonObjectKey + "," + JsonArrayIndex);
        KeyTreeList.add(new String[]{ JsonObjectKey, "String"});
        KeyTreeList.add(new String[]{ JsonArrayIndexStr,"Integer"});
      } else {
        System.out.println(layerKey);
        KeyTreeList.add(new String[]{ layerKey,"String"});
      }
    }
    return KeyTreeList;

  }

  private List<Object> getJsonArrayAndJsonObject( LinkedList<String[]> treeKeyList){
    JsonObject input = this;

    List<Object> ret = new ArrayList<>();

    ListIterator iterator = treeKeyList.listIterator();
    JsonObject jsonObject = input;
    JsonArray jsonArray = new JsonArray();
    String valuestring = new String();
    //每次循环的结果类型：JsonObject 、 JsonArray
    String LastOutType = "JsonObject";
    boolean iteratingEnd = false;
    while (iterator.hasNext()){
      String[] key = (String[]) iterator.next();
      String[] nextKey = new String[]{"",""};
      if(iterator.hasNext()){
        nextKey = (String[]) iterator.next();
      }else{
        iteratingEnd = true;
      }

      switch (key[1]+";"+nextKey[1]+";"+LastOutType){
        case "String;String;JsonObject":{
          jsonObject = jsonObject.getAsJsonObject(key[0]);
        };break;
        case "Integer;String;JsonArray":{
          jsonObject = jsonArray.get(Integer.parseInt(key[0])).getAsJsonObject();
        };break;
        case "String;Integer;JsonObject":{
          jsonArray = jsonObject.getAsJsonArray(key[0]);
        };break;
        case "Integer;Integer;JsonArray":{
          jsonArray = jsonArray.get(Integer.parseInt(key[0])).getAsJsonArray();
        };break;
        case "String;;JsonObject":
        case "Integer;;JsonObject":{
          String finalLayerClassName = jsonObject.get(key[0]).getClass().getName();
          if(jsonObject.get(key[0]) != null){
            switch (finalLayerClassName) {
              case "cn.ideartefact.gson.JsonPrimitive": {
                valuestring = jsonObject.get(key[0]).getAsString();
                LastOutType = "String";
              }
              ;
              break;
              case "cn.ideartefact.gson.JsonObject": {
                jsonObject = jsonObject.get(key[0]).getAsJsonObject();
                LastOutType = "JsonObject";
              }
              ;
              break;
              case "cn.ideartefact.gson.JsonArray": {
                jsonArray = jsonObject.get(key[0]).getAsJsonArray();
                LastOutType = "JsonArray";
              }
              ;
              break;
            }
          }
        };break;
        case "String;;JsonArray":
        case "Integer;;JsonArray":{
          String finalLayerClassName = jsonArray.get(Integer.parseInt(key[0])).getClass().getName();
          if(jsonArray.get(Integer.parseInt(key[0])) != null){
            switch (finalLayerClassName) {
              case "cn.ideartefact.gson.JsonPrimitive": {
                valuestring = jsonArray.get(Integer.parseInt(key[0])).getAsString();
                LastOutType = "String";
              }
              ;
              break;
              case "cn.ideartefact.gson.JsonObject": {
                jsonObject = jsonArray.get(Integer.parseInt(key[0])).getAsJsonObject();
                LastOutType = "JsonObject";
              }
              ;
              break;
              case "cn.ideartefact.gson.JsonArray": {
                jsonArray = jsonArray.get(Integer.parseInt(key[0])).getAsJsonArray();
                LastOutType = "JsonArray";
              }
              ;
              break;
            }
            if(jsonArray.get(Integer.parseInt(key[0])).getClass().getName().equals("cn.ideartefact.gson.JsonPrimitive")){
              valuestring=jsonArray.get(Integer.parseInt(key[0])).getAsString();
            }
          }
        }
      }

      switch (key[1]+";"+nextKey[1]+";"+LastOutType){
        case "String;String;JsonObject":
        case "Integer;String;JsonArray":{
          LastOutType = "JsonObject";
        };break;
        case "String;Integer;JsonObject":
        case "Integer;Integer;JsonArray":{
          LastOutType = "JsonArray";
        };break;
      }
      if( iteratingEnd){
        break;
      }
      iterator.previous();

    }
    ret.add(jsonObject);
    ret.add(jsonArray);
    ret.add(valuestring);
    ret.add(LastOutType);
    return ret;

  }

  private JsonObject getJsonObject(LinkedList<String[]> treeKeyList) throws JsonObjectValueTypeError {

    List<Object> ret = getJsonArrayAndJsonObject(treeKeyList);
    JsonObject jsonObject = (JsonObject) ret.get(0);
    String LastOutType = (String) ret.get(3);
    if(LastOutType!="JsonObject"){
      throw new JsonObjectValueTypeError("1001", "This key does not lead to JsonObject, it leads to a "+LastOutType+". Please confirm if the layer of the key is a JsonObject. ");
    }
    return jsonObject;
  }

  private JsonArray getJsonArray(LinkedList<String[]> treeKeyList) throws JsonObjectValueTypeError {

    List<Object> ret = getJsonArrayAndJsonObject(treeKeyList);
    JsonArray jsonArray = (JsonArray) ret.get(1);
    String LastOutType = (String) ret.get(3);

    if(LastOutType!="JsonArray"){
      throw new JsonObjectValueTypeError("1001", "This key does not lead to JsonArray , it leads to a "+LastOutType+". Please confirm if the layer of the key is a JsonArray. ");
    }
    return jsonArray;
  }


  private String  getString(LinkedList<String[]> treeKeyList) throws JsonObjectValueTypeError {

    List<Object> ret = getJsonArrayAndJsonObject(treeKeyList);
    String valuestring = (String) ret.get(2);
    String LastOutType = (String) ret.get(3);

    if(LastOutType!="String"){
      throw new JsonObjectValueTypeError("1001", "This key does not lead to String , it leads to a "+LastOutType+". Please confirm if the layer of the key is a JsonArray. ");
    }
    return valuestring;
  }

  public static class JsonObjectValueTypeError extends  Exception{
    private String code= "";
    private String msg = "";
    public JsonObjectValueTypeError(String code ,String msg){
      super(code+":"+msg);
      this.code = code;
      this.msg = msg;


    }
  }

  public static void main(String[] Args) throws  JsonObjectValueTypeError {
    JsonObject jsonObject = new Gson().fromJson("{\n" +
            "  \"members\":[ {\n" +
            "    \"sql\": \"show databases;\"\n" +
            "  }]\n" +
            "}",JsonObject.class);
    String json = jsonObject.getString("members[0].sql");
    System.out.println(json);
  }
}
