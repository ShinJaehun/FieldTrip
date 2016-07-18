package com.shinjaehun.fieldtrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class ListPlacesAdapter extends BaseAdapter {

    public static final String TAG = "ListPlacesAdatper";
    //Debuging을 위한 TAG - 나중에 삭제할 것

    private List<Place> places;
    private LayoutInflater inflater;
    private Context context;

//    public ListPlacesAdapter (Context c, List<Place> listPlaces) {
//        this.context = c;
//        this.setItems(listPlaces);
//        this.inflater = LayoutInflater.from(context);
//        //현재 context에서 LayoutInflater 얻어오기
//    }

    public ListPlacesAdapter (Context c, List<Place> listPlaces) {
        this.context = c;
        this.setItems(listPlaces);
        this.inflater = LayoutInflater.from(context);
        //현재 context에서 LayoutInflater 얻어오기
    }

    public void add (Place p) {
        places.add(p);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
        //getItems()를 호출하여 lists of places의 size 반환하기
    }

    @Override
    public Place getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
        //getItems()를 호출하여 lists of places 중에서 position에 해당하는 place 반환하기
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position ;
        //getItems()를 호출하여 lists of places 중에서 position에 해당하는 place의 id 반환하기
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        //이렇게 view holder를 만들면 처음 한번만 xml 리소스에 한번만 접근하고 getView가 호출될 때마다 findviewById를 할 필요가
        //없어지기 때문에 성능이 좋아진다고 한다.
        //http://iam1492.tistory.com/entry/ListView-View-Holder-is-dead 근데 이 자료를 보면 다시 생각을 해야 하는거 아닌가...

        if (v == null) {
            v = inflater.inflate(R.layout.list_item_place, parent, false);
            //inflater로 listview 레이아웃 inflate 선언
            holder = new ViewHolder();
            holder.iconIV = (ImageView)v.findViewById(R.id.img_icon);
            holder.nameTV = (TextView)v.findViewById(R.id.text_name);
            holder.descriptionTV = (TextView)v.findViewById(R.id.text_desc);
            //ViewHolder 초기화
            v.setTag(holder);
        } else {
            holder = (ViewHolder)v.getTag();
        }

        Place currentItem = getItem(position);
        //place 불러오기

        if (currentItem != null) {

            int resourceId = context.getResources().getIdentifier(currentItem.getPic() + "_icon", "drawable", context.getPackageName());
            //place에서 이미지 파일 resource 불러오기

            if (resourceId != 0) {
                holder.iconIV.setImageResource(resourceId);
                //list view에 이미지 불러오기
            } else {
                holder.iconIV.setImageResource(R.drawable.noimage);
                //resource 찾을 수 없으면 no image 표시하기
            }



            //이런 식으로는 DB에 저장된 자료의 수가 많을 때 처리하기 곤란하다.
//            switch (currentItem.getPic()) {
//                case "jejumuseum_icon" :
//                    holder.iconIV.setImageResource(R.drawable.jejumuseum_icon);
//                    break;
//                case "samyangprehistoric_icon" :
//                    holder.iconIV.setImageResource(R.drawable.samyangprehistoric_icon);
//                    break;
//                default:
//                    holder.iconIV.setImageResource(R.drawable.noimage);
//                    break;
//            }
            holder.nameTV.setText(currentItem.getName());
            holder.descriptionTV.setText(currentItem.getDescription());
            //list view에 장소명과 요약 정보 올리기
        }

        return v;
    }

    public List<Place> getItems() {
        //lists of places 리턴하기
        return places;
    }

    public void setItems(List<Place> places) {
        this.places = places;
    }

    class ViewHolder {
        ImageView iconIV;
        TextView nameTV;
        TextView descriptionTV;
        //CategoryActivity의 ListView에 표현될 정보 : 이미지 아이콘, 장소명, 요약 설명
    }
}
