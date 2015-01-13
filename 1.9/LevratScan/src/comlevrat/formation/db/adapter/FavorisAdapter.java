package comlevrat.formation.db.adapter;

import in.blogspot.khurram2java.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FavorisAdapter extends BaseAdapter {

	private List<Favoris> listeFav;
	private LayoutInflater inflater;
	private Context context;

	public void setFavoris(List<Favoris> listeFav) {
		this.listeFav = listeFav;
	}

	public FavorisAdapter(Context context, List<Favoris> listeFav) {
		this.listeFav = listeFav;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listeFav.size();
	}

	@Override
	public Object getItem(int position) {
		return listeFav.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listeFav.get(position).getId();
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.layout_item, null);

			holder.tvPrice = (TextView) view
					.findViewById(R.id.textViewPrice);
			holder.tvProduit = (TextView) view
					.findViewById(R.id.textViewProduit);
			holder.tvDescription = (TextView) view
					.findViewById(R.id.textViewDescription);
			holder.tvEan13 = (TextView) view
					.findViewById(R.id.textViewEan13);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		
		holder.tvPrice.setText(listeFav.get(position).getPrice()+ "");
		holder.tvProduit.setText(listeFav.get(position).getProduit() );
		holder.tvDescription.setText(listeFav.get(position).getDescription() );
		holder.tvEan13.setText(listeFav.get(position).getEan13() );

		

		return view;
	}

	private class ViewHolder {
		public TextView tvPrice;
		public TextView tvProduit;
		public TextView tvDescription;
		public TextView tvEan13;
	}

}
