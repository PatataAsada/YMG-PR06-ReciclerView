package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

public class MainActivityAdapter extends ListAdapter<Student, MainActivityAdapter.ViewHolder> {
    private final OnStudentClickListener onStudentClickListener;
    private final OnEditStudentClickListener onEditStudentClickListener;
    private final OnDeleteStudentClickListener onDeleteStudentClickListener;

    public MainActivityAdapter(OnStudentClickListener onStudentClickListener) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(),newItem.getName())&&
                        oldItem.getAge()==newItem.getAge();
            }
        });
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_main_item, parent, false), onStudentClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblName;
        private final TextView lblAge;

        public ViewHolder(View itemView, final OnStudentClickListener onStudentListener) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAge = ViewCompat.requireViewById(itemView, R.id.lblTelephone);
            itemView.setOnClickListener(v -> onStudentListener.onItemClick(getAdapterPosition()));
        }

        public void bind(Student student) {
            lblName.setText(student.getName());
            lblAge.setText(String.valueOf(student.getAge()));
        }
    }

}
