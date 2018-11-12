package edu.gvsu.cis.convcalc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import edu.gvsu.cis.convcalc.HistoryFragment.OnListFragmentInteractionListener;
import edu.gvsu.cis.convcalc.dummy.HistoryContent.HistoryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link HistoryItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HistoryAdapter extends
        SectionedRecyclerViewAdapter<HistoryAdapter.HeaderViewHolder,
                HistoryAdapter.ViewHolder,
                HistoryAdapter.FooterViewHolder> {

    private final OnListFragmentInteractionListener mListener;

    private final HashMap<String,List<HistoryItem>> dayValues;
    private final List<String> sectionHeaders;

    public HistoryAdapter(List<HistoryItem> items, OnListFragmentInteractionListener listener) {
        this.dayValues = new HashMap<String,List<HistoryItem>>();
        this.sectionHeaders = new ArrayList<String>();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        for (HistoryItem hi : items) {
            String key = "Entries for " + fmt.print(hi.timestamp);
            List<HistoryItem> list = this.dayValues.get(key);
            if (list == null) {
                list = new ArrayList<HistoryItem>();
                this.dayValues.put(key, list);
                this.sectionHeaders.add(key);
            }
            list.add(hi);
        }
        mListener = listener;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public HeaderViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.header);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    @Override
    protected int getSectionCount() {
        return this.sectionHeaders.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return this.dayValues.get(this.sectionHeaders.get(section)).size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_section_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    protected FooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }
    @Override
    protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, int section) {
        holder.header.setText(this.sectionHeaders.get(section));
    }

    @Override
    protected void onBindSectionFooterViewHolder(FooterViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int section, int position) {
        holder.mItem = this.dayValues.get(this.sectionHeaders.get(section)).get(position);
        holder.mP1.setText(holder.mItem.toString());
        holder.mDateTime.setText(holder.mItem.timestamp.toString());
        if (holder.mItem.mode.equals("Length")) {
            // length icon
            holder.mImage.setImageDrawable(holder.mImage.getResources().getDrawable(R.drawable.length_icon));
        } else {
            // volume icon
            holder.mImage.setImageDrawable(holder.mImage.getResources().getDrawable(R.drawable.volume_icon));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mP1;
        public final TextView mDateTime;
        public final ImageView mImage;
        public HistoryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mP1 = (TextView) view.findViewById(R.id.p1);
            mDateTime = (TextView) view.findViewById(R.id.timestamp);
            mImage = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateTime.getText() + "'";
        }
    }
}
