package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.ActivityMainItemBinding;

public class MainActivityAdapter extends ListAdapter<Student, MainActivityAdapter.ViewHolder> {
    private final OnEditStudentClickListener onEditStudentClickListener;
    private final OnDeleteStudentClickListener onDeleteStudentClickListener;

    public MainActivityAdapter(OnEditStudentClickListener onEditStudentClickListener, OnDeleteStudentClickListener onDeleteStudentClickListener) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getAvatar().equals(newItem.getAvatar()) &&
                        TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                        TextUtils.equals(oldItem.getEmail(), newItem.getEmail()) &&
                        oldItem.getPhonenumber() == newItem.getPhonenumber() &&
                        TextUtils.equals(oldItem.getAddress(), newItem.getAddress()) &&
                        TextUtils.equals(oldItem.getWeb(), newItem.getWeb());
            }
        });
        this.onEditStudentClickListener = onEditStudentClickListener;
        this.onDeleteStudentClickListener = onDeleteStudentClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActivityMainItemBinding mainItemBinding = ActivityMainItemBinding.inflate(inflater,parent,true);
        return new ViewHolder(mainItemBinding, onEditStudentClickListener, onDeleteStudentClickListener);
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

    @SuppressWarnings("WeakerAccess")
    static class ViewHolder extends RecyclerView.ViewHolder {

        ActivityMainItemBinding mainItemBinding;
        public ViewHolder(ActivityMainItemBinding mainItemBinding, final OnEditStudentClickListener onEditStudentClickListener, final OnDeleteStudentClickListener onDeleteStudentClickListener) {
            super(mainItemBinding.getRoot());
            this.mainItemBinding = mainItemBinding;
            mainItemBinding.btnEdit.setOnClickListener(v -> onEditStudentClickListener.onItemClick(getAdapterPosition()));
            mainItemBinding.btnDelete.setOnClickListener(v -> onDeleteStudentClickListener.onItemClick(getAdapterPosition()));
        }

        public void bind(Student student) {
            mainItemBinding.imgAvatar.setImageResource(student.getAvatar().getImageResId());
            mainItemBinding.lblName.setText(student.getName());
            mainItemBinding.lblEmail.setText(student.getEmail());
            mainItemBinding.lblPhonenumber.setText(String.valueOf(student.getPhonenumber()));
        }
    }

}
