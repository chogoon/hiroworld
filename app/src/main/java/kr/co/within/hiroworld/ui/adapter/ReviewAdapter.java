package kr.co.within.hiroworld.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxbinding.view.RxView;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.data.model.ReviewData;
import kr.co.within.hiroworld.data.model.UserData;
import kr.co.within.hiroworld.util.CircleTransform;
import rx.Observable;
import rx.subjects.PublishSubject;

import static kr.co.within.hiroworld.util.Constants.DATABASE_USERS;

/**
 * Created by chogoon on 2017-07-05.
 */

public class ReviewAdapter<T, VH extends ReviewAdapter.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private PublishSubject<View> itemViewClickSubject = PublishSubject.create();
    private final Picasso picasso;
    protected Class<VH> viewHolderClass;
    private List<T> items = new ArrayList<>();

    public ReviewAdapter(Class<VH> viewHolderClass, Picasso picasso) {
        this.viewHolderClass = viewHolderClass;
        this.picasso = picasso;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_review_list_item, parent, false);
        try {
            Constructor<VH> constructor = viewHolderClass.getConstructor(View.class);
            RxView.clicks(view)
                    .takeUntil(RxView.detaches(parent))
                    .map(aVoid -> view)
                    .subscribe(itemViewClickSubject);
            return constructor.newInstance(view);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.onBind((ReviewData) getItem(position), picasso);

    }

    public Observable<View> getItemViewClickObservable() {
        return itemViewClickSubject.asObservable();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        itemViewClickSubject.onCompleted();
    }

    public void setItems(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    public void addItem(int index, Object item) {
        List tempList = this.items;
        tempList.add(index, item);
        setItems(tempList);
        notifyItemChanged(0);
    }

    public void addItem(T item) {
        items.add(item);
        notifyItemChanged(items.size() - 1);
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void swapData(T data) {
        int position = items.indexOf(data);
        if(items.remove(data)){
            items.add(position, data);
            notifyItemChanged(position);
        }else{
            addItem(0, data);
        }
    }

    public void removeData(T data) {
        items.remove(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container_)
        LinearLayout container;

        @BindView(R.id.photo_)
        ImageView photoImage;

        @BindView(R.id.name_)
        TextView nameText;

        @BindView(R.id.content_)
        TextView contentText;

        @BindView(R.id.date_)
        TextView dateText;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void onBind(ReviewData data, Picasso picasso) {
            itemView.setTag(data);
            container.setVisibility(View.VISIBLE);
            contentText.setText(data.content);
            photoImage.setImageResource(R.drawable.googleg_standard_color_18);
            dateText.setText(new DateTime(data.write_date).toString(DateTimeFormat.forPattern("yyyy. M. d E HH:mm")));
            RxFirebaseDatabase.observeSingleValueEvent(FirebaseDatabase.getInstance().getReference().child(DATABASE_USERS)
                    .orderByChild(ReviewData.FIELD.uid.name()).equalTo(data.uid), DataSnapshot::getChildren).subscribe(userData -> {
                    if(userData.iterator().hasNext()){
                        UserData user = userData.iterator().next().getValue(UserData.class);
                        nameText.setText(user.displayName);
                        picasso.load(user.photoURL)
                                .transform(new CircleTransform())
                                .placeholder(R.drawable.googleg_standard_color_18)
                                .error(R.drawable.googleg_standard_color_18)
                                .into(photoImage);
                    }else{
                        nameText.setText("이름 없음");
                        picasso.load("temp")
                                .transform(new CircleTransform())
                                .placeholder(R.drawable.googleg_standard_color_18)
                                .error(R.drawable.googleg_standard_color_18)
                                .into(photoImage);
                    }
            });
        }
    }
}
