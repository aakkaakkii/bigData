import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsReducer extends Reducer<FriendPair, FriendArray, FriendPair, FriendArray> {
    @Override
    protected void reduce(FriendPair key, Iterable<FriendArray> values, Context context) throws IOException, InterruptedException {
        List<Friend[]> flist = new ArrayList<>();
        List<Friend> commonFriendList = new ArrayList<>();
        int count = 0;

        for (FriendArray farray : values) {
            Friend[] f = Arrays.copyOf(farray.get(), farray.get().length, Friend[].class);
            flist.add(f);
            count++;
        }

        if(count != 2) {
            return;
        }

        for (Friend outerf : flist.get(0)) {
            for (Friend innerf : flist.get(1)) {
                if (outerf.equals(innerf)) {
                    commonFriendList.add(innerf);
                }
            }
        }

        Friend[] commonFriendArray = Arrays.copyOf(commonFriendList.toArray(), commonFriendList.toArray().length, Friend[].class);
        context.write(key, new FriendArray(Friend.class, commonFriendArray));
    }
}
