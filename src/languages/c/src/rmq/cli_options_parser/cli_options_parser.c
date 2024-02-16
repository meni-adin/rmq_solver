
#include <stdbool.h>
#include "cli_options_parser.h"

#include <stdio.h>

static cop__status status;
#define RETURN_STATUS_IF_NOT_SUCCESS(status) do {if (status != COP_STATUS__SUCCESS) return status;} while (0)

static const char *has_argument_map[] = {
    [COP__NO_ARGUMENT]       = "",
    [COP__REQUIRED_ARGUMENT] = ":",
    [COP__OPTIONAL_ARGUMENT] = "::",
};

size_t
get_optstring_length(cop__option *options, size_t options_len)
{
    size_t optstring_length = 1; // Initialize with 1 for null-terminator

    for (size_t option_idx = 0; option_idx < options_len; ++option_idx)
    {
        if (options[option_idx].definition.short_option != COP__SHORT_OPTION_NULL)
        {
            ++optstring_length; // For option character
            switch (options[option_idx].definition.has_argument)
            {
            case COP__NO_ARGUMENT:
                optstring_length += 0;
                break;
            case COP__REQUIRED_ARGUMENT:
                optstring_length += 1;
                break;
            case COP__OPTIONAL_ARGUMENT:
                optstring_length += 2;
                break;
            default:
                return COP_STATUS__INVALID_PARAMETER;
                break;
            }
        }
    }

    return optstring_length;
}

cop__status
cop__parse(int argc, char *argv[], cop__option *options, size_t options_len)
{
    size_t optstring_length;

    optstring_length = get_optstring_length(options, options_len);
    RETURN_STATUS_IF_NOT_SUCCESS(status);

    printf("optstring_length: %zu\n", optstring_length);
    return COP_STATUS__SUCCESS;
}
