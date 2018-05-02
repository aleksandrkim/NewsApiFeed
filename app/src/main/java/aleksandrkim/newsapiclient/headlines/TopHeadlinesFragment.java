package aleksandrkim.newsapiclient.headlines;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import aleksandrkim.newsapiclient.R;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TopHeadlinesFragment extends Fragment {
    public static final String TAG = "TopHeadlinesFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindString(R.string.top_headlines)
    String screenTitle;
    private Unbinder unbinder;

    private HeadlinesVM headlinesVM;
    private HeadlinesAdapter headlinesAdapter;
    private OnHeadlinePicked mListener;

    public TopHeadlinesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        headlinesVM = ViewModelProviders.of(requireActivity()).get(HeadlinesVM.class);
        setAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_headlines, container, false);
        unbinder = ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        requireActivity().setTitle(screenTitle);
        setRecyclerView();
        return v;
    }

    private void setAdapter() {
        headlinesAdapter = new HeadlinesAdapter((v, position) -> {
            Log.d(TAG, "clicked: " + headlinesAdapter.getHeadline(position).getTitle());
        });

        headlinesVM.getArticles().observe(this, articles -> {
            Log.d(TAG, "articles observed " + articles.size());

            if (articles.size() == 0) {
                headlinesVM.refreshAllArticles();
                Toast.makeText(requireActivity(), "Headlines are being downloaded", Toast.LENGTH_SHORT).show();
            } else
                headlinesAdapter.setArticles(articles);
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(headlinesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnHeadlinePicked) {
//            mListener = (OnHeadlinePicked) context;
//        } else {
//            throw new RuntimeException(context.toString()  + " must implement OnHeadlinePicked");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnHeadlinePicked {
        void onHeadlinePicked(int id);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
