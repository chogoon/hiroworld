package kr.co.within.hiroworld.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.within.hiroworld.R;
import kr.co.within.hiroworld.ui.model.SplashModel;
import kr.co.within.hiroworld.util.MyLog;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by chogoon on 2017-07-05.
 */

public class MenuAdapter<T, VH extends MenuAdapter.MenuViewHolder>
        extends RecyclerView.Adapter<VH> {

    private PublishSubject<View> itemViewClickSubject = PublishSubject.create();

    protected Class<VH> viewHolderClass;
    private List<T> items = new ArrayList<>();

    public MenuAdapter(Class<VH> viewHolderClass) {
        this.viewHolderClass = viewHolderClass;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        MyLog.e("onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_menu_list_item, parent, false);
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
        holder.onBind(getItem(position));

    }

    public Observable<View> getItemViewClickObservable() {
        return itemViewClickSubject.asObservable();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        MyLog.e("onDetachedFromRecyclerView");
//        itemViewClickSubject.onCompleted();
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

    public void swapData(T data) {
        int position = items.indexOf(data);
        if(items.remove(data)){
            items.add(position, data);
        }else{
            addItem(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        items.add(item);
        notifyItemChanged(items.size() - 1);
    }

    public T getItem(int position) {
        return items.get(position);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_)
        TextView name;

        public MenuViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void onBind(Object data) {
            itemView.setTag(data);
            name.setText(SplashModel.SplashMenus.valueOf(String.valueOf(data)).getName());
        }
    }
}
