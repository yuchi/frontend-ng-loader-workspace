This document explores the different delivery options available for a new loder / infrastructure / npm integration

## Liferay Portal 7.0

For released versions of Liferay Portal 7.0 (included in Liferay DXP), there are several options:

### Hot replacement

If we consider the current implementation _buggy_, then it could be argued that a fix is needed, which will find
its way back to live installations via the already scheduled fix-pack, service-pack and GA releases.

This new mechanism would automatically override the existing one and **should maintain 100% backwards compatibiliy**.

### Builtin opt-in

A less constrained option than the hot replacement would be a builtin opt-in loading mechanism that could live side by
side with the current one that could be explicitly enabled by a developer.

This could be released as a set of new modules that aren't enabled by default but can be activated to provide additional
features to the platform.

### Marketplace opt-in

The previous builtin opt-in version could be developed and delivered as a Marketplace application. This would raise some
coding requirements that could derive from living inside the liferay-portal repo, but will make discoverability a bit harder
than the previous options.

## Liferay Portal 7.1

When targetting unreleased versions of Liferay Portal, bigger changes are allowed as long as **there is a clear and
documented upgrade path** that disrupts the upgrade process as less as possible.

It is important to mention that a Liferay Portal 7.1 release date hasn't yet been discussed, so the timeframe for this
approach to reach real users would be unclear.