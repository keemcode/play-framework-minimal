package filters;

import play.api.http.EnabledFilters;
import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;
import play.mvc.EssentialFilter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Filters extends DefaultHttpFilters {

    @Inject
    public Filters(EnabledFilters enabledFilters, CORSFilter corsFilter) {
        super(combine(enabledFilters.asJava().getFilters(), corsFilter.asJava()));
    }

    private static List<EssentialFilter> combine(List<EssentialFilter> filters, EssentialFilter toAppend) {
        List<EssentialFilter> combinedFilters = new ArrayList<>(filters);
        combinedFilters.add(toAppend);
        return combinedFilters;
    }
}
