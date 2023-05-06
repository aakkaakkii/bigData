import com.google.gson.JsonArray;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class FriendsMapper extends Mapper<LongWritable, Text, FriendPair, FriendArray> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer st = new StringTokenizer(value.toString(), "\t");
        String person = st.nextToken();
        String friends = st.nextToken();

        Friend f1 = populateFriend(person);
        List<Friend> friendList = populateFriendList(friends);
        Friend[] friendArray = Arrays.copyOf(friendList.toArray(), friendList.toArray().length, Friend[].class);
        FriendArray farray = new FriendArray(Friend.class, friendArray);

        for (Friend f2 : friendList) {
            FriendPair fpair = new FriendPair(f1, f2);
            context.write(fpair, farray);
        }
    }

    private List<Friend> populateFriendList(String friends) {
        List<Friend> friendList = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            Object obj = (Object) parser.parse(friends);
            JsonArray jsonArray = (JsonArray) obj;

            for (Object jobj : jsonArray) {
                friendList.add(populateFriend((String) jobj));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return friendList;

    }

    private Friend populateFriend(String friendJson) {
        JSONParser parser = new JSONParser();
        Friend friend = null;

        try {
            Object obj = (Object) parser.parse(friendJson);
            JSONObject jsonObject = (JSONObject) obj;

            Long lid = (long) jsonObject.get("id");
            IntWritable id = new IntWritable(lid.intValue());
            Text name = new Text((String) jsonObject.get("name"));
            Text homeTown = new Text((String) jsonObject.get("homeTown"));
            friend = new Friend(id, name, homeTown);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return friend;
    }
}
