package org.calcwiki.data.storage;

import android.app.Fragment;

import org.calcwiki.ui.fragment.PageFragment;
import org.calcwiki.ui.fragment.SearchFragment;

import java.io.Serializable;
import java.util.Stack;

/**
 * 存储当前 Fragment 信息，用于返回键回退等等
 *
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
public class CurrentFragment implements Serializable {

    public final static int SEARCH_FRAGMENT = 1;
    public final static int PAGE_FRAGMENT = 2;

    // used to initialize fragment with extString
    public abstract static class InitializibleFragment extends Fragment {
        public abstract void initialize(String param);
    }

    public static CurrentFragment currentFragment;

    public static CurrentFragment getInstance() {
        if (currentFragment == null) {
            currentFragment = new CurrentFragment();
        }
        return currentFragment;
    }

    public static void restoreInstance(Serializable instance) {
        currentFragment = (CurrentFragment) instance;
    }

    public Stack<FragmentInfo> infoStack = new Stack<FragmentInfo>();

    public int currentIndex = -1;

    public class FragmentInfo implements Serializable {

        public int kind;
        public String tag;
        // For Search Fragment, extString shortages keyWord
        // For page Fragment, extString shortages url
        public String extString;

        public InitializibleFragment getReinitializeFragmentInstance() {
            InitializibleFragment fragment;
            if (kind == CurrentFragment.SEARCH_FRAGMENT) {
                fragment = new SearchFragment();
            } else if (kind == CurrentFragment.PAGE_FRAGMENT) {
                fragment = new PageFragment();
            } else {
                return null;
            }
            fragment.initialize(extString);
            return fragment;
        }
    }

    public void push(InitializibleFragment fragment) {
        if (!infoStack.empty() && infoStack.peek().tag.equals(fragment.getTag())) {  // avoid add the same fragment in the same time
            return;
        }
        FragmentInfo info = new FragmentInfo();
        info.tag = fragment.getTag();
        if (fragment instanceof SearchFragment) {
            info.kind = SEARCH_FRAGMENT;
            info.extString = ((SearchFragment) fragment).getKeyWord();
            infoStack.push(info);
            currentIndex++;
        } else if (fragment instanceof PageFragment) {
            info.kind = PAGE_FRAGMENT;
            info.extString = ((PageFragment) fragment).getPageName();
            infoStack.push(info);
            currentIndex++;
        } else {
            throw new IllegalArgumentException("Please add to CurrentFragment before you storage its info");
        }
    }

    public FragmentInfo getCurrentFragmentInfo() {
        try {
            return infoStack.peek();
        } catch (Throwable t) {
            return null;
        }
    }

    public FragmentInfo getPrevFragmentInfo() {
        infoStack.pop();
        return infoStack.peek();
    }

    public boolean hasPrev() {
        return !(infoStack.empty() || infoStack.size() == 1);
    }

}
