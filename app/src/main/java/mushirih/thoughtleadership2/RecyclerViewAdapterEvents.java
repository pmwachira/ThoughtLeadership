package mushirih.thoughtleadership2;

/**
 * Created by p-tah on 21/10/2015.
 */

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapterEvents extends RecyclerView.Adapter<RecyclerViewAdapterEvents.ViewHolder> implements View.OnClickListener {

    private List<EventItem> items;
    private OnItemClickListener onItemClickListener;
    EventItem item;
    Context passed;
    public RecyclerViewAdapterEvents(List<EventItem> items, Context events) {
        this.items = items;
        this.passed=events;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_events, parent, false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {

        item = items.get(position);

       if(!item.getDate().equals("null")) {
           String month="";
           switch (item.getMonth()){

               case "1":
                   month="January";
                   break;

               case "2":
                   month="February";
                   break;
               case "3":
                   month="March";
                   break;
               case "4":
                   month="April";
                   break;
               case "5":
                   month="May";
                   break;
               case "6":
                   month="June";
                   break;
               case "7":
                   month="July";
                   break;
               case "8":
                   month="August";
                   break;
               case "9":
                   month="September";
                   break;
               case "10":
                   month="October";
                   break;
               case "11":
                   month="November";
                   break;
               case "12":
                   month="December";
                   break;
           }
           holder.date.setText(item.getDate());
           holder.month.setText(month);
           holder.title.setText(item.getTitle());
           holder.owner.setText(item.getOwner());
           holder.itemView.setTag(item);

       }else {
          // if(items.size()==0) {

               holder.ting.setVisibility(View.GONE);
               holder.title.setText("There are no upcoming events at this time");

               holder.owner.setVisibility(View.GONE);
               holder.owner.setVisibility(View.GONE);
           holder.itemView.setTag(item);
           //}
        }

    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {

                    onItemClickListener.onItemClick(v, (EventItem) v.getTag());
                }
            }, 200);
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date,month,title,owner;
        public LinearLayout ting;
        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            month = (TextView) itemView.findViewById(R.id.month);
            title = (TextView) itemView.findViewById(R.id.desc);
            owner = (TextView) itemView.findViewById(R.id.owner);
            ting= (LinearLayout) itemView.findViewById(R.id.dateThing);

        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, EventItem item);

    }
}