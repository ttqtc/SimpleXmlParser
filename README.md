# SimpleXmlParser
A simple xml parser

Comments and tag will be filtered.

⚠️ Not fully tested, Please do not use it in production environment


## How to use it

```java
String xml = "<xml>... something xml comtent";
Map<String,Object> map = SimpleXmlParser.parse(xml);
```
  
## Example
 
xml content like:
  
```xml
  <xml>
    <ToUserName>
        <![CDATA[gh_e136c6e50636]]>
    </ToUserName>
    <FromUserName>
        <![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]>
    </FromUserName>
    <CreateTime>1408091189</CreateTime>
    <MsgType>
        <![CDATA[event]]>
    </MsgType>
    <Event>
        <![CDATA[location_select]]>
    </Event>
    <EventKey>
        <![CDATA[6]]>
    </EventKey>
    <SendLocationInfo>
        <Location_X>
            <![CDATA[23]]>
        </Location_X>
        <Location_Y>
            <![CDATA[113]]>
        </Location_Y>
        <Scale>
            <![CDATA[15]]>
        </Scale>
        <Label>
            <![CDATA[Tower D, 3rd Block]]>
        </Label>
        <Poiname>
            <![CDATA[]]>
        </Poiname>
    </SendLocationInfo>
</xml>
```
parse result map for the xml:

```json
{
    "xml": {
        "CreateTime": "1408091189",
        "EventKey": "6",
        "SendLocationInfo": {
            "Label": "Tower D, 3rd Block",
            "Poiname": "",
            "Scale": "15",
            "Location_X": "23",
            "Location_Y": "113"
        },
        "Event": "location_select",
        "ToUserName": "gh_e136c6e50636",
        "FromUserName": "oMgHVjngRipVsoxg6TuX3vz6glDg",
        "MsgType": "event"
    }
}
```

## More
If you want to convert a map into an object, you can use a third-party tool, such as hutool.

```java
String xml = "<xml>... something xml comtent";
Map<String,Object> map = SimpleXmlParser.parse(xml);

YourEntity entity = BeanUtil.mapToBean(map, YourEntity.class, false, CopyOptions.create());
```
