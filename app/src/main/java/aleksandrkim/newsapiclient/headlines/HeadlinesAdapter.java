package aleksandrkim.newsapiclient.headlines;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import aleksandrkim.newsapiclient.R;
import aleksandrkim.newsapiclient.database.models.Article;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandr Kim on 03 May, 2018 1:20 AM for Newsapiclient
 */

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.HeadlinesVH> {

    private RecyclerItemClickListener recyclerItemClickListener;
    private List<Article> articles;

    public HeadlinesAdapter(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @NonNull
    @Override
    public HeadlinesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_row, parent, false);
        final HeadlinesVH headlinesVH = new HeadlinesVH(v);
        v.setOnClickListener(view -> recyclerItemClickListener.onItemClick(view,  headlinesVH.getAdapterPosition()));
        return headlinesVH;
    }

    public void setArticles(final List<Article> newArticles) {
        if (this.articles == null) {
            this.articles = newArticles;
            notifyItemRangeInserted(0, newArticles.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return articles.size();
                }

                @Override
                public int getNewListSize() {
                    return newArticles.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return articles.get(oldItemPosition).getId() == newArticles.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return newArticles.get(newItemPosition).equals(articles.get(oldItemPosition));
                }
            });
            this.articles = newArticles;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlinesVH holder, int position) {
        final Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.sourceName.setText(article.getSourceName());
        holder.description.setText(article.getDescription());
        Picasso.get().load(article.getUrlToImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public Article getHeadline(int position) {
        return articles.get(position);
    }

    static class HeadlinesVH extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.source_name)
        TextView sourceName;
        @BindView(R.id.description)
        TextView description;

        HeadlinesVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
