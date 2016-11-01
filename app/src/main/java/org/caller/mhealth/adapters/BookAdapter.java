package org.caller.mhealth.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.Book;
import org.caller.mhealth.entitys.BookList;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class BookAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<BookList> mItems;
    private  static  int k;

    public BookAdapter(Context context, List<BookList> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ret;
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.healthy_book_list, parent, false);
        ret = new BookViewHolder(itemView,this);
        return ret;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BookList booklist = mItems.get(position);
//        if(booklist!=null){
        BookViewHolder bookViewHolder = (BookViewHolder) holder;
        bookViewHolder.bindView(booklist);
//        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SparseArrayCompat<View> mViews;
        BookAdapter mAdapter;

        public BookViewHolder(View itemView, BookAdapter adapter) {
            super(itemView);
            mViews = new SparseArrayCompat<>();
            mAdapter=adapter;
//            for (int i = 0; i < 4; i++) {
//                View view = getChildView("healthy_book_view_" + i);
//                if (view != null) {
//                    //每一个Body内部的视频点击事件处理
//                    view.setOnClickListener(this);
//                }
//            }
        }

        View getChildView(int rid) {
            View ret;
            ret = mViews.get(rid);
            if (ret == null) {
                ret = itemView.findViewById(rid);
                if (ret != null) {
                    mViews.put(rid, ret);
                }
            }
            return ret;
        }

        View getChildView(String name) {
            View ret = null;
            if (name != null) {
                //使用反射动态获取多个控件的ID
                Class aClass = R.id.class;
                int id = 0;
                try {
                    Field field = aClass.getDeclaredField(name);
                    field.setAccessible(true);
                    id = field.getInt(aClass); //获取类成员的数值
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (id != 0) {
                    ret = getChildView(id);
                }
            }
            return ret;
        }

        void bindView(BookList item) {
            switch (item.getId()) {
                case 1:
                    ImageView bookTypeicon1 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon1.setImageResource(R.drawable.bookicon1);
                    break;
                case 2:
                    ImageView bookTypeicon2 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon2.setImageResource(R.drawable.bookicon2);
                    break;
                case 3:
                    ImageView bookTypeicon3 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon3.setImageResource(R.drawable.bookicon3);
                    break;
                case 4:
                    ImageView bookTypeicon4 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon4.setImageResource(R.drawable.bookicon4);
                    break;
                case 5:
                    ImageView bookTypeicon5 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon5.setImageResource(R.drawable.bookicon5);
                    break;
                case 6:
                    ImageView bookTypeicon6 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon6.setImageResource(R.drawable.bookicon6);
                    break;
                case 7:
                    ImageView bookTypeicon7 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon7.setImageResource(R.drawable.bookicon7);
                    break;
                case 8:
                    ImageView bookTypeicon8 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon8.setImageResource(R.drawable.bookicon8);
                    break;
                case 9:
                    ImageView bookTypeicon9 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon9.setImageResource(R.drawable.bookicon9);
                    break;
                case 10:
                    ImageView bookTypeicon10 = (ImageView) getChildView(R.id.book_type_icon);
                    bookTypeicon10.setImageResource(R.drawable.bookicon10);
                    break;
            }

            TextView txt = (TextView) getChildView(R.id.healthy_book_classify_title);
            String type = item.getType();
            if (type != null) {
                txt.setText(type);
            }

            TextView txtsum = (TextView) getChildView(R.id.healthy_book_classify_num);
            long classifysum = item.getTotal();
            txtsum.setText(String.valueOf(classifysum));

            List<Book> list = item.getList();
            //默认四个
            if (list.size() > 4) {
                for (int i=0; i < 4; i++) {
                    Book book = list.get(i);
                    View view = getChildView("healthy_book_card_" + i);
                    view.setOnClickListener(this);
                    view.setTag(book);
                    ImageView imageView = (ImageView) getChildView("healthy_book_view_" + i);
                    if (imageView != null) {
                        String cover = book.getImg();
                        if (cover != null) {
                            //TODO:显示图片
                            Context context = imageView.getContext();
                            Picasso.with(context)
                                    .load("http://tnfs.tngou.net/img" + cover)
                                    .config(Bitmap.Config.RGB_565)
                                    .resize(320, 200)
                                    .into(imageView);
                        }
                    }
                    TextView txtBookTitle = (TextView) getChildView("healthy_book_title_" + i);
                    if (txtBookTitle != null) {
                        String name = book.getName();
                        if (name != null) {
                            txtBookTitle.setText(name);
                        }
                    }

                    TextView txtCount = (TextView) getChildView("healthy_book_rcount_" + i);
                    if (txtCount != null) {
                        long Play = book.getRcount();
                        txtCount.setText(String.valueOf(Play));
                    }

                    TextView txtAuthor = (TextView) getChildView("healthy_book_author_" + i);
                    if (txtAuthor != null) {
                        String author = book.getAuthor();
                        if (author != null) {
                            txtAuthor.setText(author);
                        }
                    }
                }

                TextView mchange = (TextView) getChildView("book_item_change");
                mchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("test", "onClick: "+111);
                        mAdapter.notifyDataSetChanged();
                    }
                });

            } else {
                //TODO:未来需要根据个数进行适配，代码动态添加布局
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Book book = list.get(i);
                    View view = getChildView("healthy_book_card_" + i);
                    view.setOnClickListener(this);
                    view.setTag(book);
                    ImageView imageView = (ImageView) getChildView("healthy_book_view_" + i);
                    if (imageView != null) {
                        String cover = book.getImg();
                        if (cover != null) {
                            //TODO:显示图片
                            Context context = imageView.getContext();
                            Picasso.with(context)
                                    .load(cover)
                                    .config(Bitmap.Config.RGB_565)
                                    .resize(320, 200)
                                    .into(imageView);
                        }
                    }
                    TextView txtBookTitle = (TextView) getChildView("healthy_book_title_" + i);
                    if (txtBookTitle != null) {
                        String title = book.getName();
                        if (title != null) {
                            txtBookTitle.setText(title);
                        }
                    }

                    TextView txtCount = (TextView) getChildView("healthy_book_rcount_" + i);
                    if (txtCount != null) {
                        long Play = book.getRcount();
                        txtCount.setText(String.valueOf(Play));
                    }

                    TextView txtAuthor = (TextView) getChildView("healthy_book_author_" + i);
                    if (txtAuthor != null) {
                        String author = book.getAuthor();
                        if (author != null) {
                            txtAuthor.setText(author);
                        }
                    }
                }
                for (int i = size; i < 4; i++) {
                    CardView imageView = (CardView) getChildView("healthy_book_card_" + i);
                    imageView.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void onClick(View v) {

        }
    }

}
