package kr.co.within.hiroworld.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.data.model.BuildingData;
import rx.Observable;
import rx.subjects.PublishSubject;

import static android.R.id.list;
import static kr.co.within.hiroworld.util.Constants.IMAGE_SERVER;

/**
 * Created by chogoon on 2017-07-05.
 */

public class BuildingsAdapter<T, VH extends BuildingsAdapter.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private PublishSubject<View> itemViewClickSubject = PublishSubject.create();
    private final Picasso picasso;
    protected Class<VH> viewHolderClass;
    private List<T> items = new ArrayList<>();

    public BuildingsAdapter(Class<VH> viewHolderClass, Picasso picasso) {
        this.viewHolderClass = viewHolderClass;
        this.picasso = picasso;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_building_list_item, parent, false);
        try {
            Constructor<VH> constructor = viewHolderClass.getConstructor(View.class);
            RxView.clicks(view)
                    .takeUntil(RxView.detaches(parent))
                    .map(aVoid -> view)
                    .subscribe(itemViewClickSubject);
            return constructor.newInstance(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.onBind((BuildingData) getItem(position), picasso);

    }

    public Observable<View> getItemViewClickObservable() {
        return itemViewClickSubject.asObservable();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        itemViewClickSubject.onCompleted();
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    public void addItem(int index, Object item) {
        List tempList = this.items;
        tempList.add(index, item);
        setItems(tempList);
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
        }
        notifyDataSetChanged();
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

        @BindView(R.id.photo_)
        ImageView photo;

        @BindView(R.id.name_)
        TextView name;

        @BindView(R.id.review_count_)
        TextView reviewCount;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void onBind(BuildingData data, Picasso picasso) {
            itemView.setTag(data);
            name.setText(data.name);
            picasso.load(IMAGE_SERVER + data.imagePath)
//                    .transform(new CircleTransform())
                    .placeholder(R.drawable.googleg_standard_color_18)
                    .error(R.drawable.googleg_standard_color_18)
                    .resize(360, 360)
                    .into(photo);
            reviewCount.setText(String.valueOf(data.reviews.size()));

        }
    }
}
