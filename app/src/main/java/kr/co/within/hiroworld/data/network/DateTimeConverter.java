package kr.co.within.hiroworld.data.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTimeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    private static final DateTimeFormatter formatter = ISODateTimeFormat.dateTimeParser();

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.print(src));
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTimeParser();
        try {
            return new DateTime(json.getAsLong() * 1000);
        } catch (UnsupportedOperationException e) {
            Date date = context.deserialize(json, Date.class);
            return new DateTime(date);
        } catch (Exception e){
            return fmt.parseDateTime(json.getAsString());
        }
    }
}