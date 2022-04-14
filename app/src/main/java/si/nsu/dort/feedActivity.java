package si.nsu.dort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class feedActivity extends AppCompatActivity {


    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



        CardStackView cardStackView = findViewById(R.id.card_stack_view);

        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d("OK", "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d("OK", "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    Toast.makeText(feedActivity.this, "Direction Right", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left){
                    Toast.makeText(feedActivity.this, "Direction Left", Toast.LENGTH_SHORT).show();
                }


                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d("OK", "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d("OK", "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                Button temp = view.findViewById(R.id.profile_btn);
                TextView tv = view.findViewById(R.id.item_bio);

                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),profileActivity.class);
                        startActivity(intent);
                    }
                });
                Log.d("OK", "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_bio);
                Log.d("OK", "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });

        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);

        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

    }

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();

        DBhelper db= new DBhelper(this);

        //Get and set General Info

        Cursor cursor= db.getAllInfo();

        StringBuilder stringBuilder= new StringBuilder();

         Cursor temp_cursor;
         int temp=0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                stringBuilder.append("Name:"+cursor.getString(0)
                        +"\nNSU ID :"+cursor.getInt(1)
                        +"\nSex :" +cursor.getString(2)
                        +"\nBio :" +cursor.getString(3));
                temp_cursor = db.getResearchInfobyID(cursor.getInt(1));
                String temp_O = db.get_research_infos(temp_cursor);
                String temp_1 = db.get_research_interests(temp_cursor);
                items.add(new ItemModel(R.drawable.rinterestbox, stringBuilder.toString(), temp_1, temp_O));
                // move to the next row
                stringBuilder = new StringBuilder();
                cursor.moveToNext();
            }
        }


        //Get and set Research Info



        items.add(new ItemModel(R.drawable.rinterestbox, "Marpuah", "20", "Malang"));
        items.add(new ItemModel(R.drawable.rinterestbox, "Sukijah", "27", "Jonggol"));
        items.add(new ItemModel(R.drawable.rinterestbox, "Markobar", "19", "Bandung"));
        items.add(new ItemModel(R.drawable.rinterestbox, "Marmut", "25", "Hutan"));

        items.add(new ItemModel(R.drawable.rinterestbox, "Markonah", "24", "Jember"));
        items.add(new ItemModel(R.drawable.rinterestbox, "Marpuah", "20", "Malang"));
        items.add(new ItemModel(R.drawable.rinterestbox, "Sukijah", "27", "Jonggol"));
        items.add(new ItemModel(R.drawable.rinterestbox, "Markobar", "19", "Bandung"));
        items.add(new ItemModel(R.drawable.rinterestbox, "Marmut", "25", "Hutan"));
        return items;
    }


    }




